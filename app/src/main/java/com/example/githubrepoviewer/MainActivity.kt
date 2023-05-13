package com.example.githubrepoviewer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepoviewer.databinding.ActivityMainBinding
import com.example.githubrepoviewer.extensions.loadImage
import com.example.githubrepoviewer.model.entities.Item
import com.example.githubrepoviewer.ui.IonItemClickListener
import com.example.githubrepoviewer.ui.RepoAdapter
import com.example.githubrepoviewer.ui.RepoViewModel


class MainActivity : AppCompatActivity(), IonItemClickListener {

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
        repoAdapter = RepoAdapter(this)

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

    override fun onItemClick(item: Item) {
        onButtonShowPopupWindowClick(item)
        Toast.makeText(this,"${item.name}",Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun onButtonShowPopupWindowClick(item: Item) {

        // inflate the layout of the popup window
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.activity_repo_item, null)

        // create the popup window
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // lets taps outside the popup also dismiss it
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(binding.root, Gravity.CENTER, 0, 0)

        val imageView = popupView.findViewById<ImageView>(R.id.avatar)
        val repoName = popupView.findViewById<TextView>(R.id.repoName)
        val stars = popupView.findViewById<TextView>(R.id.star)
        val language = popupView.findViewById<TextView>(R.id.language)
        val desc = popupView.findViewById<TextView>(R.id.description)

        item.owner?.avatarUrl?.let { imageView.loadImage(it,false) }
        repoName.text = item.name
        stars.text = "Star = ${item.stargazersCount}"
        language.text = "Language: ${item.language}"
        desc.text = item.description

    }

}