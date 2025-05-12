package com.nca.yourdentist.presentation.screens.patient.home.examination.questionnaire

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Question
import com.nca.yourdentist.navigation.PatientScreens
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.surfaceVariantLight
import com.nca.yourdentist.presentation.screens.patient.home.examination.ExaminationViewModel
import com.nca.yourdentist.presentation.screens.patient.home.examination.questionnaire.components.NavigationButtons
import com.nca.yourdentist.presentation.screens.patient.home.examination.questionnaire.components.PageNumberIndicator
import com.nca.yourdentist.presentation.screens.patient.home.examination.questionnaire.components.QuestionItem
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun QuestionnaireScreen(
    navController: NavController,
    viewModel: ExaminationViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val questions = remember {
        mutableStateListOf(
            Question(title = context.getString(R.string.question_1)),
            Question(title = context.getString(R.string.question_2)),
            Question(title = context.getString(R.string.question_3)),
            Question(title = context.getString(R.string.question_4))
        )
    }

    val pagerState = rememberPagerState(pageCount = { questions.size })
    val coroutineScope = rememberCoroutineScope()

    // State Management
    var answeredCount by remember { mutableIntStateOf(0) }

    var progress by remember { mutableFloatStateOf(.25f) }
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "progress")

    Scaffold(
        topBar = { TopApplicationBar(title = stringResource(id = R.string.questionnaire)) {} }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title and Progress
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.answer_the_following_questions),
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                PageNumberIndicator(
                    currentPage = pagerState.currentPage + 1,
                    totalPages = questions.size
                )
            }

            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = primaryLight,
                trackColor = surfaceVariantLight,
                strokeCap = StrokeCap.Round
            )

            // Question Pager
            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
                val currentQuestion = questions[page]
                QuestionItem(
                    question = currentQuestion.title,
                    initialAnswer = currentQuestion.answer,
                    onAnswerSelected = { answer ->
                        questions[pagerState.currentPage].answer = answer
                        questions.forEach {
                            Log.e("Questionnaire screen", it.answer)
                        }
                    }
                )
            }

            // Navigation Buttons
            NavigationButtons(
                pagerState = pagerState,
                questions = questions,
                onNextClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    answeredCount++
                    progress = (answeredCount.toFloat() / questions.size.toFloat()) + .25f
                },
                onPrevClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                    answeredCount--
                    progress = (answeredCount.toFloat() / questions.size.toFloat()) + .25f
                },
                onSubmitClick = {
                    navController.navigate(PatientScreens.Results.route)
                }
            )
        }
    }
}