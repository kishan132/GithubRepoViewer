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
  
  
  
![WhatsApp Image 2023-05-07 at 17 46 33 (2)](https://user-images.githubusercontent.com/56019933/236676981-00465efc-98e8-46f9-bb8b-91f2f2824eaf.jpeg)
![WhatsApp Image 2023-05-07 at 17 46 33 (1)](https://user-images.githubusercontent.com/56019933/236676984-d40a26e7-e567-44d4-99ca-9ec0a1190325.jpeg)
![WhatsApp Image 2023-05-07 at 17 46 33](https://user-images.githubusercontent.com/56019933/236676987-733b4251-6d81-4877-8374-8dfe771f882d.jpeg)
