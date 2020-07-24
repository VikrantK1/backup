package com.creater.backup.installedApps

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.creater.backup.R
import com.creater.backup.databinding.AppsToBackUpFragmentBinding
import com.creater.backup.services.serviceutlis
import com.creater.backup.toastmessage
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.apps_to_back_up_fragment.*

class AppsToBackUp : Fragment() {

   lateinit var share:SharedPreferences
   private var viewModelAdapter:BackUpAdapter?=null
    private lateinit var viewModel: AppsToBackUpViewModel
    lateinit var viewModelFactory: AppsToBackUpViewModelFactory
     lateinit var binding: AppsToBackUpFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.apps_to_back_up_fragment,container,false)
        viewModelFactory=AppsToBackUpViewModelFactory(requireContext())
        viewModel=ViewModelProvider(this,viewModelFactory).get(AppsToBackUpViewModel::class.java)
        binding.viewModel=viewModel
        viewModelAdapter= BackUpAdapter(OnClickListener {
            viewModel.selected_Apps(it)
        })
        binding.AppBackup.adapter=viewModelAdapter
        viewModel.listOfApps.observe(viewLifecycleOwner, Observer {
            it.apply {
                viewModelAdapter?.appList=it
            }
        })
        binding.backupBtn.setOnClickListener {
            viewModel.BackupButtonClicked()
        }
        binding.lifecycleOwner=viewLifecycleOwner
        share=requireContext().getSharedPreferences("service",Context.MODE_PRIVATE)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var editshare=share.edit()
        when(item.itemId)
        {

            R.id.Size->{
                editshare.putBoolean("installed",false)
                editshare.putBoolean("size",true)
                editshare.putBoolean("name",false)
                editshare.commit()
                viewModel.sortData()
            }
            R.id.name ->{
                editshare.putBoolean("installed",false)
                editshare.putBoolean("size",false)
                editshare.putBoolean("name",true)
                editshare.commit()
                viewModel.sortData()
                }
            R.id.date ->{
                editshare.putBoolean("installed",true)
                editshare.putBoolean("size",false)
                editshare.putBoolean("name",false)
                editshare.commit()
                viewModel.sortData()}
        }

        return true
    }

}