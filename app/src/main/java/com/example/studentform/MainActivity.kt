package com.example.studentform

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.studentform.ui.theme.*
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentFormScreen()
        }
    }
}

@Composable
fun StudentFormScreen() {
    val context = LocalContext.current

    var nameState by remember { mutableStateOf("") }
    var surnameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var dateState by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var isAgreed by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DisposableEffect(Unit) {
            val calendar = Calendar.getInstance()
            val dialog = DatePickerDialog(
                context,
                { _, year, month, day ->
                    dateState = "%02d/%02d/%04d".format(day, month + 1, year)
                    showDatePicker = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.setOnDismissListener { showDatePicker = false }
            dialog.show()
            onDispose { if (dialog.isShowing) dialog.dismiss() }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppPrimary)
                .padding(start = 20.dp, end = 20.dp, top = 26.dp, bottom = 26.dp)
        ) {

            Column {
                Text(
                    text = "Student Form",
                    style = HeaderTitleStyle,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Fill in your details",
                    style = HeaderSubtitleStyle,
                    color = Color.White.copy(alpha = 0.80f)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            FormLabel("Your Name")
            OutlinedTextField(
                value = nameState,
                onValueChange = { nameState = it },
                placeholder = { Text("Enter your name", style = FieldTextStyle, color = AppHint) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                colors = appFieldColors()
            )

            FormLabel("Your Surname")
            OutlinedTextField(
                value = surnameState,
                onValueChange = { surnameState = it },
                placeholder = { Text("Enter your surname", style = FieldTextStyle, color = AppHint) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                colors = appFieldColors()
            )

            FormLabel("Select Date")
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = dateState,
                    onValueChange = {},
                    placeholder = { Text("Pick a date", style = FieldTextStyle, color = AppHint) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    readOnly = true,
                    enabled = false,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Pick a date",
                            tint = AppBorder
                        )
                    },
                    colors = appDateFieldColors()
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { showDatePicker = true }
                )
            }

            FormLabel("Email Address")
            OutlinedTextField(
                value = emailState,
                onValueChange = { emailState = it },
                placeholder = { Text("your.email@example.com", style = FieldTextStyle, color = AppHint) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                colors = appFieldColors()
            )

            FormLabel("თქვენი ფავორიტი მიმართულება")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppSurface, shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                directionOptions.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedOption = option }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = selectedOption == option,
                            onClick = { selectedOption = option },
                            colors = appRadioColors()
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = option,
                            style = RadioOptionStyle,
                            color = AppText
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppSurface, shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Switch(
                    checked = isAgreed,
                    onCheckedChange = { isAgreed = it },
                    colors = appSwitchColors()
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "ვეთანხმები წესებს და პირობებს",
                    style = SwitchLabelStyle,
                    color = AppText
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    val allTextFilled = nameState.isNotBlank()
                            && surnameState.isNotBlank()
                            && emailState.isNotBlank()
                            && dateState.isNotBlank()

                    if (!allTextFilled || selectedOption.isEmpty() || !isAgreed) {
                        Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "მონაცემები გაიგზავნა!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(10.dp),
                colors = appButtonColors()
            ) {
                Text(
                    text = "Submit",
                    style = ButtonTextStyle,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun FormLabel(text: String) {
    Text(
        text = text,
        style = FormLabelStyle,
        color = AppText
    )
}
