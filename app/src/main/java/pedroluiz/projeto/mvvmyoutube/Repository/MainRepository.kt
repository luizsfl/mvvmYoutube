package pedroluiz.projeto.mvvmyoutube.Repository

import pedroluiz.projeto.mvvmyoutube.rest.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllLives() = retrofitService.getAllLives()

}

