package com.aulasandroid.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aulasandroid.calculadoraimc.ui.theme.CalculadoraIMCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraIMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculadoraImcScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CalculadoraImcScreen(modifier: Modifier = Modifier) {

    var altura by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("0.0") }
    var classificacao by remember { mutableStateOf("") }
    var corResultado by remember { mutableStateOf(Color.Green) }

    Column(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(color = colorResource(R.color.cor_app)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.bmi),
                contentDescription = "Imc Logo",
                modifier = Modifier
                    .size(80.dp)
                    .padding(vertical = 16.dp)
            )

            Text(
                text = "Calculadora IMC",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .offset(y = (-30).dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF9F6F6)
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Seus dados",
                        fontSize = 24.sp,
                        color = colorResource(R.color.cor_app),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = altura,
                        onValueChange = { altura = it },
                        label = { Text("Altura ") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(35)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = peso,
                        onValueChange = { peso = it },
                        label = { Text("Peso ") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(35)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.cor_app)
                        ),
                        onClick = {

                            val alturaValor = altura.toFloatOrNull()
                            val pesoValor = peso.toFloatOrNull()

                            if (alturaValor != null && pesoValor != null) {

                                val imc = pesoValor / (alturaValor * alturaValor)
                                resultado = String.format("%.1f", imc)

                                when {
                                    imc < 18.5 -> {
                                        classificacao = "Abaixo do peso"
                                        corResultado = Color.Red
                                    }

                                    imc < 25 -> {
                                        classificacao = "Peso ideal"
                                        corResultado = Color.Green
                                    }

                                    imc < 30 -> {
                                        classificacao = "Levemente acima do peso"
                                        corResultado = Color.Yellow
                                    }

                                    imc < 35 -> {
                                        classificacao = "Obesidade grau I"
                                        corResultado = Color.Red
                                    }

                                    imc < 40 -> {
                                        classificacao = "Obesidade grau II"
                                        corResultado = Color.Red
                                    }

                                    else -> {
                                        classificacao = "Obesidade grau III"
                                        corResultado = Color.Red
                                    }
                                }
                            }
                        }
                    ) {

                        Text(
                            text = "CALCULAR",
                            color = Color.White,
                            fontSize = 18.sp
                        )

                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(100.dp),
                colors = CardDefaults.cardColors(corResultado)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = resultado,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = classificacao,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}