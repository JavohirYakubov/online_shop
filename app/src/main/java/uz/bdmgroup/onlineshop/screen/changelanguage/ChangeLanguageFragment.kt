package uz.bdmgroup.onlineshop.screen.changelanguage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.fragment_change_language.*
import uz.bdmgroup.onlineshop.R
import uz.bdmgroup.onlineshop.screen.MainActivity

class ChangeLanguageFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_language, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =  ChangeLanguageFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnUzbek.setOnClickListener {
            Hawk.put("pref_lang", "uz")
            requireActivity().finish()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
        btnEnglish.setOnClickListener {
            requireActivity().finish()
            Hawk.put("pref_lang", "en")
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }

    }
}