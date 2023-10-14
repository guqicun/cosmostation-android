package wannabit.io.cosmostaion.common

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import wannabit.io.cosmostaion.R
import wannabit.io.cosmostaion.ui.main.CosmostationApp
import wannabit.io.cosmostaion.ui.password.AppLockActivity
import wannabit.io.cosmostaion.ui.password.PasswordCheckActivity

open class BaseActivity : AppCompatActivity() {

    var isMainActivity: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFullScreen()
    }

    private fun initFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            val controller = window.insetsController
            if (controller != null) {
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(BaseUtils.updateResources(base))
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch(Dispatchers.IO) {
            if (CosmostationApp.instance.needShowLockScreen()) {
                val intent = Intent(this@BaseActivity, AppLockActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_fade_out)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isMainActivity) {
            moveTaskToBack(true)
        } else {
            moveTaskToBack(false)
            toMoveBack()
        }
    }
}