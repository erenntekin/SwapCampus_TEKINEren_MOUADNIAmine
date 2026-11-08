package com.example.swapcampus_mamaabbas.composeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.swapcampus_mamaabbas.composeapp.resources.*
import com.example.swapcampus_mamaabbas.composeapp.ui.components.icon
import com.example.swapcampus_mamaabbas.composeapp.ui.components.labelRes
import com.example.swapcampus_mamaabbas.composeapp.viewmodel.FormErrors
import com.example.swapcampus_mamaabbas.shared.domain.Category
import com.example.swapcampus_mamaabbas.shared.domain.Listing
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ListingFormScreen(
    isEditing: Boolean,
    initial: Listing?,
    errors: FormErrors,
    onSave: (title: String, description: String, price: String, category: Category, photoUrl: String?) -> Unit,
    onBack: () -> Unit,
) {
    var title by rememberSaveable(initial) { mutableStateOf(initial?.title ?: "") }
    var price by rememberSaveable(initial) {
        mutableStateOf(initial?.price?.let { if (it % 1.0 == 0.0) it.toLong().toString() else it.toString() } ?: "")
    }
    var category by rememberSaveable(initial) { mutableStateOf(initial?.category ?: Category.BOOKS) }
    var description by rememberSaveable(initial) { mutableStateOf(initial?.description ?: "") }
    var photoUrl by rememberSaveable(initial) { mutableStateOf(initial?.photoUrl ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(if (isEditing) Res.string.form_edit_title else Res.string.form_post_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.Close, contentDescription = stringResource(Res.string.back))
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            errors.general?.let { Text(stringResource(it), color = MaterialTheme.colorScheme.error) }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(Res.string.field_title)) },
                isError = errors.title != null,
                supportingText = errors.title?.let { { Text(stringResource(it)) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text(stringResource(Res.string.field_price)) },
                suffix = { Text("€") },
                isError = errors.price != null,
                supportingText = errors.price?.let { { Text(stringResource(it)) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Text(stringResource(Res.string.section_category), style = MaterialTheme.typography.titleSmall)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Category.entries.forEach { entry ->
                    FilterChip(
                        selected = category == entry,
                        onClick = { category = entry },
                        label = { Text(stringResource(entry.labelRes())) },
                        leadingIcon = { Icon(entry.icon(), contentDescription = null, modifier = Modifier.size(18.dp)) },
                    )
                }
            }

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(Res.string.field_description)) },
                minLines = 4,
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = photoUrl,
                onValueChange = { photoUrl = it },
                label = { Text(stringResource(Res.string.field_photo_url)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Button(
                onClick = { onSave(title, description, price, category, photoUrl.ifBlank { null }) },
                enabled = !isEditing || initial != null,
                modifier = Modifier.fillMaxWidth().height(52.dp),
            ) {
                Text(stringResource(if (isEditing) Res.string.save_changes else Res.string.publish))
            }
        }
    }
}
