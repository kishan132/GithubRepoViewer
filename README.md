This project is created for testing some Github APIs.

In this Project :  I have Created a search field, which queries on public repository.
                   which Fetch the data of the repos and create a card for each repo.  


// this is curl we can use to get repo response on search with a query
curl -L \
  -H "Accept: application/vnd.github+json" \
  -H "Authorization: Bearer <YOUR-TOKEN>"\
  -H "X-GitHub-Api-Version: 2022-11-28" \
  https://api.github.com/search/repositories?q=Q
  
  
  User can also Sort Repo list according to no of Stars, name, updated date, score and watcher_count
  
  
  
