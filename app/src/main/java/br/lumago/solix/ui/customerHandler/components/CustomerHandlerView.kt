package br.lumago.solix.ui.customerHandler.components

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.lumago.solix.ui.theme.boldStyle
import br.lumago.solix.ui.theme.corTexto
import br.lumago.solix.ui.theme.normalStyle
import br.lumago.solix.ui.theme.titleStyle
import br.lumago.solix.ui.utils.components.Header
import kotlinx.coroutines.launch

enum class CustomerTabs(
    val text: String,
) {
    Geral("Geral"),
    Endereco("Endereço"),
    Contato("Contato")
}

@Composable
fun CustomerHandlerView() {
    val activity = LocalActivity.current!!
    // Extra
    val customerIdExtra = activity.intent.getLongExtra("customerId", 0L)
    //
    val title = when (customerIdExtra) {
        0L -> "Novo cliente"
        else -> "Editar cliente"
    }

    val pagerState = rememberPagerState(pageCount = { CustomerTabs.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Header(
            title,
            activity
        )

        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier.fillMaxWidth(),
            contentColor = Color.Black,
            containerColor = Color.White,
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .height(4.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(corTexto, shape = RoundedCornerShape(50))
                )
            }
        ) {
            CustomerTabs.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    text = {
                        Text(
                            text = currentTab.text,
                            style = boldStyle
                        )
                    }
                )
            }
        }

    }
}