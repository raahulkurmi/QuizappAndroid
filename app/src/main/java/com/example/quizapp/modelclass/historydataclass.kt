package com.example.quizapp.modelclass

class historydataclass{
    var datetime:Long=0L
    var coin:String=""
    var iswithdrawl:Boolean=false
    constructor()
    constructor(datetime: Long, coin: String, iswithdrawl: Boolean) {
        this.datetime = datetime
        this.coin = coin
        this.iswithdrawl = iswithdrawl
    }

}


