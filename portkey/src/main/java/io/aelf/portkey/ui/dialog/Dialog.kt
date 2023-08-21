package io.aelf.portkey.ui.dialog

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.aelf.portkey.async.PortkeyAsyncCaller
import io.aelf.portkey.ui.basic.wrapperStyle
import io.aelf.portkey.utils.log.GLogger


internal object Dialog {

    private var isActive by mutableStateOf(false)
    private var dialogProps by mutableStateOf(DialogProps())

    internal fun isBusy(): Boolean {
        return isActive
    }

    internal fun show(dialogProps: DialogProps) {
        this.dialogProps = dialogProps
        isActive = true
    }

    internal fun hide() {
        isActive = false
    }

    @Composable
    internal fun BasicDialog() {
        if (isActive) {
            val fadeInAlpha by animateFloatAsState(
                targetValue = if (isActive) 1f else 0f,
                animationSpec = tween(durationMillis = 1000, easing = FastOutLinearInEasing),
                label = "dialog"
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .pointerInput(Unit) { }
                    .alpha(fadeInAlpha),
                contentAlignment = Alignment.Center
            ) {
                DialogBody()
            }
        }

    }

    @Composable
    private fun DialogBody() {
        Box(
            modifier = Modifier
                .width(wrapperStyle.width)
                .wrapContentHeight(Alignment.CenterVertically)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 24.dp,
                        bottom = 32.dp
                    )
                    .width(288.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = dialogProps.mainTitle,
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color(0xFF162736),
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight(700)
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = dialogProps.subTitle,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color(0xFF414852),
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight(400)
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    @Composable
    private fun Buttons(){
        Row (
            modifier = Modifier.width(288.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){

        }
    }

}

open class DialogProps {
    var mainTitle: String = "Continue ?"
    var subTitle: String = "Are you sure you want to continue ?"
    var positiveText: String = "Confirm"
    var negativeText: String = "Cancel"
    var positiveCallback: (() -> Unit) = {}
    var negativeCallback: (() -> Unit) = {}
}


@Preview
@Composable
fun PreviewDialog() {
    val dialogProps = remember {
        DialogProps().apply {
            positiveCallback = {
                GLogger.e("positiveCallback")
            }
            negativeCallback = {
                GLogger.e("negativeCallback")
            }
        }
    }
    Dialog.BasicDialog()
    PortkeyAsyncCaller.asyncCall {
        Thread.sleep(200)
        Dialog.show(dialogProps)
    }
}