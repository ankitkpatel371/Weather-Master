package com.weather.master.ui.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.weather.master.custom.CustomProgressDialog
import com.weather.master.databinding.FBaseLayoutBinding

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    private lateinit var customProgressDialog: CustomProgressDialog


    protected lateinit var viewModel: VM
    protected abstract fun initializeViewModel(): VM

    private var fBaseBinding: FBaseLayoutBinding? = null

    abstract fun setUpUI()

    protected abstract fun getLayoutView(
        inflater: LayoutInflater
    ): View?

    protected open fun getProgressView(): View? {
        return fBaseBinding?.vContentLoadProgress
    }

    companion object {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fBaseBinding = FBaseLayoutBinding.inflate(inflater)

        val layoutView = getLayoutView(inflater)

        fBaseBinding?.fBaseLayoutContent?.addView(layoutView)

        viewModel = initializeViewModel()

        readArguments()

        setUpObserver()

        return fBaseBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
    }

    private fun setUpObserver() {
        viewModel.showProgress.observe(viewLifecycleOwner, { show ->
            if (show) {
                showProgress()
            } else {
                hideProgress()
            }
        })


    }

    public fun showProgress() {
        getProgressView()?.visibility = View.VISIBLE

    }

    public fun hideProgress() {
        getProgressView()?.visibility = View.GONE

    }

    fun hideSoftKeyboard() {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).hideSoftKeyboard()
        }
    }

    fun showSoftKeyboard(view: View) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).showSoftKeyboard(view)
        }
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
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).showMessage(isSuccess, message)
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    fun showToast(@StringRes resId: Int) {
        Toast.makeText(requireContext(), resId, Toast.LENGTH_SHORT).show()
    }

    fun isInternetAvailable(): Boolean {
        return (activity as BaseActivity<*>).isInternetAvailable()
    }

    fun showNoInternetAvailable() {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).showNoInternetAvailable()
        }
    }

    private fun showCustomProgressDialog() {
        showCustomProgressDialog(false)
    }


    private fun showCustomProgressDialog(canCancel: Boolean) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).showCustomProgressDialog(canCancel)
        } else {
            customProgressDialog = CustomProgressDialog(requireContext())
            customProgressDialog.show()
            customProgressDialog.setCancelable(canCancel)
        }
    }

    private fun hideCustomProgressDialog() {

        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).hideCustomProgressDialog()
        } else {
            if (::customProgressDialog.isInitialized && customProgressDialog.isShowing) {
                customProgressDialog.dismiss()
            }
        }

    }

    open fun readArguments() {}

    fun getBaseActivity(): BaseActivity<*> {
        return activity as BaseActivity<*>
    }
}
