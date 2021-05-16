package com.weather.master.ui.base


import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.weather.master.R
import com.weather.master.custom.CustomProgressDialog
import com.weather.master.databinding.ABaseLayoutBinding
import com.weather.master.utils.ConnectivityLiveData


abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    private var baseBinding: ABaseLayoutBinding? = null
    private val TAG = this::class.java.simpleName

    private lateinit var customProgressDialog: CustomProgressDialog

    protected lateinit var viewModel: VM
    protected abstract fun initializeViewModel(): VM

    companion object {

    }

    protected abstract fun getLayoutView(): View?

    protected abstract fun setUpChildUI(savedInstanceState: Bundle?)

    open fun readIntent() {}

    protected open fun getProgressView(): View? {
        return baseBinding?.vContentLoadProgress
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseBinding = ABaseLayoutBinding.inflate(layoutInflater)
        setContentView(baseBinding?.root)

        baseBinding?.aBaseLayoutContent?.addView(getLayoutView())

        viewModel = initializeViewModel()
        setBaseUpObserver()

        ConnectivityLiveData(application)
            .observe(this, Observer { isConnected ->
                onNetworkConnectionChanged(isConnected)
            })

        readIntent()
        setUpChildUI(savedInstanceState)
    }

    private fun setBaseUpObserver() {
        viewModel.showProgress.observe(this, Observer { show ->
            if (show) {
                showProgress()
            } else {
                hideProgress()
            }
        })
    }

    fun showProgress() {
        getProgressView()?.visibility = View.VISIBLE
    }

    fun hideProgress() {
        getProgressView()?.visibility = View.GONE
    }

    fun hideSoftKeyboard() {
        val view = currentFocus
        if (view != null) {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    fun showSoftKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
        inputMethodManager.toggleSoftInputFromWindow(
            view.getApplicationWindowToken(),
            InputMethodManager.SHOW_FORCED, 0
        );
    }


    fun showMessage(@StringRes resId: Int) {
        showMessage(true, getString(resId))
    }

    fun showMessage(message: String) {
        showMessage(true, message)
    }

    fun showMessage(isSuccess: Boolean, @StringRes resId: Int) {
        showMessage(isSuccess, getString(resId))
    }

    fun showMessage(isSuccess: Boolean, message: String) {
        val snackbar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val textView =
            snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(if (isSuccess) Color.GREEN else Color.WHITE)
        textView.maxLines = 3
        snackbar.show()
    }

    /**
     * CHECK INTERNET CONNECTIVITY.
     */

    fun isInternetAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm?.activeNetworkInfo
        return netInfo != null && netInfo.isConnected && netInfo.isAvailable
    }

    fun showNoInternetAvailable() {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.msg_no_internet_available),
            Snackbar.LENGTH_LONG
        )
        val textView =
            snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(Color.RED)
        snackbar.show()
    }


    private fun onNetworkConnectionChanged(isConnected: Boolean) {
        onNetworkStatusChanged(isConnected)

    }

    abstract fun onNetworkStatusChanged(isConnected: Boolean)

    fun showCustomProgressDialog() {
        showCustomProgressDialog(false)
    }


    fun showCustomProgressDialog(canCancel: Boolean) {
        customProgressDialog = CustomProgressDialog(this)
        customProgressDialog.show()
        customProgressDialog.setCancelable(canCancel)
    }

    fun hideCustomProgressDialog() {
        if (::customProgressDialog.isInitialized && customProgressDialog.isShowing()) {
            customProgressDialog.dismiss()
        }
    }


}
