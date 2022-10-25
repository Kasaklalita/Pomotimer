package com.kasaklalita.pomotimer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kasaklalita.pomotimer.ui.theme.PomotimerTheme
import androidx.compose.ui.text.style.TextAlign
import com.kasaklalita.pomotimer.database.DatabaseHandler
import com.kasaklalita.pomotimer.models.QuoteModel

class MainActivity : ComponentActivity() {
    private var dbHandler = DatabaseHandler(this)

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PomotimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                                title = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceAround
                                    ) {
                                        Text(
                                            "Simple TopAppBar",
                                            maxLines = 1
                                        )
                                    }
                                },
                                navigationIcon = {
                                    IconButton(onClick = { /* doSomething() */ }) {
                                        Icon(
                                            imageVector = Icons.Filled.Menu,
                                            contentDescription = "Localized description"
                                        )
                                    }
                                },
                            )
                        },
                        modifier = Modifier.fillMaxSize(),
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(
                                        start = 24.dp,
                                        end = 24.dp,
                                        top = 88.dp,
                                        bottom = 24.dp
                                    ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    MainTimer()
                                    ResumeButton()
                                }
                                QuoteCard(
                                    "The quote of the day",
                                    "theQuoteOfTheDay!!.quote",
                                    "theQuoteOfTheDay!!.author"
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    StageInfo()
                                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                        Icon(
                                            imageVector = Icons.Default.PlayArrow,
                                            contentDescription = null,
                                            modifier = Modifier.size(36.dp),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                        Icon(
                                            imageVector = Icons.Default.Home,
                                            contentDescription = null,
                                            modifier = Modifier.size(36.dp),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    fun ResumeButton() {
        OutlinedButton(
            onClick = {
//                val newQuote = QuoteModel(0, "Lorem ipsum dolor sit amet", "Veniamin Polienko")
//                val addQuote = dbHandler.addQuote(newQuote)

            },
            shape = CircleShape,
            contentPadding = PaddingValues(8.dp),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(65.dp),
            )
        }
    }

    @Composable
    fun MainTimer() {
        Button(
            modifier = Modifier.size(200.dp),
            shape = CircleShape,
            onClick = {
                val quotesList: ArrayList<QuoteModel> = dbHandler.getQuotesList()
                if (quotesList.size > 0) {
                    Toast.makeText(this, "Quote: ${quotesList[0]}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "No quotes", Toast.LENGTH_LONG).show()
                }
            }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "25:00", style = MaterialTheme.typography.headlineLarge)
                Text(text = "FOCUS", style = MaterialTheme.typography.titleMedium)
            }
        }
    }

    @Composable
    fun StageInfo() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "1/4",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Reset",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    @Composable
    fun QuoteCard(title: String, quote: String, author: String) {
        var theQuoteOfTheDay = dbHandler.getQuotesList().random()
        Log.e("QUOTE", "$theQuoteOfTheDay")
        Card() {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,

                    )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = theQuoteOfTheDay.quote,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = theQuoteOfTheDay.author,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        PomotimerTheme {
            Greeting("Android")
        }
    }
}
