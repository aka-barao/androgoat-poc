package com.example.androgoatpoc.ui.ids

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.androgoatpoc.databinding.FragmentIdsBinding

class InsecureDataStorageFragment : Fragment() {

    private var _binding: FragmentIdsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val insecureDataStorageViewModel =
            ViewModelProvider(this).get(InsecureDataStorageViewModel::class.java)

        _binding = FragmentIdsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textFilesContent
        insecureDataStorageViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonGetSdcardFiles.setOnClickListener {

            val rootDirectory = Environment.getExternalStorageDirectory()
            val filesList = rootDirectory.listFiles { dir, name -> name.startsWith("users") && name.endsWith("tmp") }
            binding.textFilesContent.text = ""

            if (filesList != null) {
                for (file in filesList) {
                    if(file.isFile){
                        val fileContent = file.readText()
                        binding.textFilesContent.append("File name:\n ${file.name}:\n " +
                                "File content:\n $fileContent \n\n")
                        Log.i("MeuApp", "Conteúdo do arquivo ${file.name}: $fileContent")
                    }
                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}