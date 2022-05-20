package uz.mnsh.qazo.presentation.main.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import uz.mnsh.qazo.databinding.FragmentEditBinding

@AndroidEntryPoint
class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? =null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)



        return binding.root
    }

}