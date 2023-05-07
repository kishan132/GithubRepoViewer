package com.example.githubrepoviewer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepoviewer.data.RepoData
import com.example.githubrepoviewer.model.entities.Item
import kotlinx.coroutines.launch

class RepoViewModel : ViewModel() {

    var _repoData = MutableLiveData<List<Item>?>()
    val repoData : LiveData<List<Item>?> = _repoData

    fun getUserRepoList(query:String,page:Int,sortType:String) = viewModelScope.launch {
        RepoData.getUserRepoList(query = query,page = page, sortType = sortType).let {
            _repoData.postValue(it)
        }
    }

}