package com.nca.yourdentist.presentation.screens.patient.questionnaire

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.data.model.ItemQuestion
import com.nca.yourdentist.navigation.Screen
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.surfaceVariantLight
import kotlinx.coroutines.launch

val questions = listOf(
    ItemQuestion("Do you suffer from any pain with cold drinks or sweets?", yesOrNoQuestion = true),
    ItemQuestion("Do you experience discomfort when biting?", yesOrNoQuestion = true),
    ItemQuestion("How often do you brush your teeth?", yesOrNoQuestion = true),
    ItemQuestion("Have you had a dental check-up in the last 6 months?", yesOrNoQuestion = true)
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun QuestionnaireScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { questions.size })
    val scope = rememberCoroutineScope()
    var answeredCount by remember { mutableIntStateOf(questions.count { it.answer.isNotEmpty() }) }
    val adjustedProgress by remember { derivedStateOf { answeredCount.toFloat() / questions.size } }
    val animatedProgress by animateFloatAsState(
        targetValue = adjustedProgress,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing),
        label = stringResource(R.string.progress_animation)
    )

    Scaffold(
        topBar = { TopApplicationBar(title = stringResource(id = R.string.questionnaire)) {} }) { padding ->
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
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.answer_the_following_questions),
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Row {
                    AnimatedContent(
                        targetState = pagerState.currentPage + 1, transitionSpec = {
                            slideIntoContainer(
                                towards = Up, animationSpec = tween(300)
                            ) togetherWith slideOutOfContainer(
                                towards = Up, animationSpec = tween(300)
                            )
                        }, label = "Page Number Animation"
                    ) { animatedPageNumber ->
                        Text(
                            text = "$animatedPageNumber",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = " / ${questions.size}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

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
            HorizontalPager(
                state = pagerState, modifier = Modifier.weight(1f)
            ) { page ->
                val currentQuestion = questions[page]

                QuestionItem(
                    question = currentQuestion.title,
                    initialAnswer = currentQuestion.answer,
                    onAnswerSelected = { answer ->
                        if (currentQuestion.answer.isEmpty()) {
                            answeredCount++
                        }
                        currentQuestion.answer = answer
                    })
            }
            // Navigation Buttons
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (pagerState.currentPage > 0) {
                    OutlinedButton(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            stringResource(
                                id = R.string.previous
                            ),
                            modifier = Modifier.padding(vertical = 8.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Button(
                    onClick = {
                        if (pagerState.currentPage < questions.size - 1) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            navController.navigate(Screen.PatientResults.route)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    enabled = questions[pagerState.currentPage].answer.isNotEmpty()
                ) {
                    Text(
                        text =
                            if (pagerState.currentPage == questions.size - 1) stringResource(id = R.string.submit)
                            else stringResource(id = R.string.next),
                        modifier = Modifier.padding(vertical = 8.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionItem(
    question: String, initialAnswer: String?, onAnswerSelected: (String) -> Unit
) {
    var selectedAnswer by remember { mutableStateOf(initialAnswer) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Question Text
        Text(
            text = question,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AnswerButton(
                text = stringResource(id = R.string.yes),
                isSelected = selectedAnswer == "Yes",
                onClick = {
                    selectedAnswer = "Yes"
                    onAnswerSelected("Yes")
                })
            AnswerButton(
                text = stringResource(id = R.string.no),
                isSelected = selectedAnswer == "No",
                onClick = {
                    selectedAnswer = "No"
                    onAnswerSelected("No")
                })
        }
    }
}

@Composable
fun AnswerButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) primaryLight else Color.White
        ),
        border = BorderStroke(1.dp, primaryLight),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else primaryLight
            )
        }
    }
}