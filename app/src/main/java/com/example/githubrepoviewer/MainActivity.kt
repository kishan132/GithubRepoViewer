package com.example.githubrepoviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepoviewer.databinding.ActivityMainBinding
import com.example.githubrepoviewer.model.entities.Item
import com.example.githubrepoviewer.ui.RepoAdapter
import com.example.githubrepoviewer.ui.RepoViewModel

class MainActivity : AppCompatActivity() {

    private val TAG = "githubtest"
    private lateinit var binding:ActivityMainBinding
    private var filterType : String = "name"
    private lateinit var repoViewModel: RepoViewModel
    private lateinit var repoAdapter : RepoAdapter
    private var isScrolled:Boolean = false
    private var query:String? =null
    private var totalItemList : List<Item>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repoViewModel = ViewModelProvider(this)[RepoViewModel::class.java]
        repoAdapter = RepoAdapter()

        binding.repoRV.apply{
            layoutManager =LinearLayoutManager(context)
            adapter = repoAdapter
        }

        binding.repoRV.addOnScrollListener(rvOnScrollListener)

        val filterList = listOf("name", "stars", "watchers_count", "updated", "score")
        val spinnerAdapter:ArrayAdapter<String> = ArrayAdapter(this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,filterList)
        binding.filter.adapter = spinnerAdapter

        binding.filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                filterType = filterList[p2]
                binding.selectedFilter.text = filterType
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // nothing selected
                binding.selectedFilter.text = filterList[0]
            }
        }

        repoViewModel.repoData.observe(this, Observer {

            Log.d(TAG, "observe: $it")
            binding.progressBar.visibility = View.GONE

            if(totalItemList==null && it!=null){
                totalItemList = it
            }
            else if(!it.isNullOrEmpty()){
                totalItemList = totalItemList!! + it
            }

//            Log.d(TAG, "it: ${it?.size} -> $it")
//            Log.d(TAG, "totalItemList: ${totalItemList?.size} -> $totalItemList")

            repoAdapter.submitList(totalItemList)
        })

        binding.submitBtn.setOnClickListener {

            query = binding.query.text.toString()

            if(query.isNullOrEmpty()){
                Toast.makeText(this,"Please Enter Query",Toast.LENGTH_SHORT).show()
            }
            else{
                // fetch data
                totalItemList = null
                repoAdapter.submitList(null)

                binding.progressBar.visibility = View.VISIBLE
                Toast.makeText(this,"query- ${binding.query.text} filterType- $filterType",Toast.LENGTH_SHORT).show()
                repoViewModel.getUserRepoList(query!!,1,filterType)
            }
        }

    }


    private val rvOnScrollListener : RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener(){

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            isScrolled=true
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val totalItem : Int = binding.repoRV.layoutManager!!.itemCount
            val currentItem : Int = binding.repoRV.layoutManager!!.childCount
            val scrolledItem : Int = (binding.repoRV.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

            if(isScrolled && (currentItem + scrolledItem == totalItem)){
                isScrolled=false
                //calculate next page
                val page : Int = (totalItemList?.size ?: 1) /30 +1
                binding.progressBar.visibility = View.VISIBLE
                repoViewModel.getUserRepoList(query!!, page,filterType)
            }
        }
    }

}