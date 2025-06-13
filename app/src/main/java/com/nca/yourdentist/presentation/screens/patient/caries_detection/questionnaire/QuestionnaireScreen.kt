package com.nca.yourdentist.presentation.screens.patient.caries_detection.questionnaire

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Questionnaire
import com.nca.yourdentist.navigation.PatientScreens
import com.nca.yourdentist.presentation.component.ui.customized.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.surfaceVariantLight
import com.nca.yourdentist.presentation.screens.patient.caries_detection.ExaminationViewModel
import com.nca.yourdentist.presentation.screens.patient.caries_detection.questionnaire.components.NavigationButtons
import com.nca.yourdentist.presentation.screens.patient.caries_detection.questionnaire.components.PageNumberIndicator
import com.nca.yourdentist.presentation.screens.patient.caries_detection.questionnaire.components.QuestionItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun QuestionnaireScreen(
    navController: NavController, viewModel: ExaminationViewModel
) {
    val context = LocalContext.current
    val questions = listOf(
        context.getString(R.string.question_1),
        context.getString(R.string.question_2),
        context.getString(R.string.question_3),
        context.getString(R.string.question_4)
    )
    var answers = remember { mutableStateListOf("", "", "", "") }
    val pagerState = rememberPagerState(pageCount = { questions.size })
    val coroutineScope = rememberCoroutineScope()
    var answeredCount by remember { mutableIntStateOf(0) }
    var progress by remember { mutableFloatStateOf(.25f) }
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "progress")

    Scaffold(
        topBar = { TopApplicationBar(title = stringResource(id = R.string.questionnaire)) {} }) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_background),
                contentDescription = stringResource(R.string.background),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.answer_the_following_questions),
                        fontSize = 18.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp),
                        maxLines = 2
                    )
                    PageNumberIndicator(
                        currentPage = pagerState.currentPage + 1, totalPages = questions.size
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

                HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
                    val currentQuestion = questions[page]
                    QuestionItem(
                        question = currentQuestion,
                        initialAnswer = answers[page],
                        onAnswerSelected = { answers[page] = it }
                    )
                }

                NavigationButtons(
                    pagerState = pagerState, questions = questions, answers = answers,
                    onNextClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        answeredCount++
                        progress = (answeredCount.toFloat() / questions.size.toFloat()) + .25f
                    }, onPrevClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                        answeredCount--
                        progress = (answeredCount.toFloat() / questions.size.toFloat()) + .25f
                    }, onSubmitClick = {
                        Log.e("QuestionnaireScreen", "Answers: $answers")
                        val questionnaireResult = getResult(questions, answers)
                        navController.apply {
                            viewModel.updateQuestionnaire(questionnaireResult)
                            navigate(PatientScreens.Results.route) {
                                popUpTo(PatientScreens.Home.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

fun getResult(questions: List<String>, answers: List<String>): List<Questionnaire> =
    listOf(
        Questionnaire(questions[0], answers[0]),
        Questionnaire(questions[1], answers[1]),
        Questionnaire(questions[2], answers[2]),
        Questionnaire(questions[3], answers[3])
    )