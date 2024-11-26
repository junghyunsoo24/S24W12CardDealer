package kr.ac.kumoh.s20191091.s24w12carddealer

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.ac.kumoh.s20191091.s24w12carddealer.ui.theme.S24W12CardDealerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            S24W12CardDealerTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val viewModel: CardViewModel = viewModel()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            Modifier.padding(innerPadding),
        ) {
            CardSection(viewModel)
            ShuffleButton {
                viewModel.shuffle()
            }
        }
    }
}

@Composable
@SuppressLint("DiscouragedApi")
fun ColumnScope.CardSection(viewModel: CardViewModel) {
    val cards by viewModel.cards.observeAsState(emptyList())
    val context = LocalContext.current

    if(cards.isEmpty())
        return

    val cardResources = IntArray(5)

    cards.forEachIndexed { index, cardName ->
        cardResources[index] = context.resources.getIdentifier(
            cardName,
            "drawable",
            context.packageName
        )
    }
//    cardResources[0] = R.drawable.c_10_of_spades
//    cardResources[1] = R.drawable.c_jack_of_spades2
//    cardResources[2] = R.drawable.c_queen_of_spades2
//    cardResources[3] = R.drawable.c_king_of_spades2
//    cardResources[4] = R.drawable.c_ace_of_spades

    CardImages(cardResources)
}

@Composable
fun ColumnScope.CardImages(res: IntArray)        {
    if (LocalConfiguration.current.orientation
        == Configuration.ORIENTATION_LANDSCAPE
    ) {
        // weight(1f)은 onCreate()에 있는 Column에 적용됨
        // 버튼을 맨 밑에 위치시키기 위함
        Row(
            modifier = Modifier
                .weight(1f)
                .background(Color(0, 100, 0))
        ) {
            res.forEachIndexed { index, res ->
                Image(
                    painter = painterResource(res),
                    contentDescription = "card ${index + 1}",
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(4.dp)
                        .weight(1f)
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(Color(0, 0, 100))
        ) {
            Row(Modifier.weight(1f)) {
                CardImageView(res[0] , "Card 1")
                CardImageView(res[1] , "Card 2")

            }

            Row(Modifier.weight(1f)) {
                CardImageView(res[2] , "Card 3")
                CardImageView(res[3] , "Card 4")

            }

            Row(Modifier.weight(1f)) {
                CardImageView(res[4] , "Card 5")
            }
//            // Row의 weight는 세로 화면에서 균등 분배
//            Row(Modifier.weight(1f)) {
//
//
//                Row(Modifier.weight(1f)) {
//                    Image(
//                        painter = painterResource(res[4]),
//                        contentDescription = "5th card",
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .padding(4.dp)
//                            .weight(1f)
//                    )
//                }
//            }
        }
    }
}

@Composable
fun RowScope.CardImageView(res: Int, desc: String) {
    Image(
        painter = painterResource(res),
        contentDescription = desc,
        modifier = Modifier
            .fillMaxHeight()
            .padding(4.dp)
            .weight(1f)
    )
}

@Composable
fun ShuffleButton(function: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        //fillMaxSize만 하면 밀어내버림
        onClick = {

        }
    ) {
        Text(stringResource(R.string.good_luck))
    }
}