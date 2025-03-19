package ru.otus.basicarchitecture.net

data class AddressData(val suggestions:List<AddressValue>)

data class AddressValue(val value:String)

data class AddressQuery(val query:String)