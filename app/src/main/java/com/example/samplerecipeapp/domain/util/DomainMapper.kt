package com.example.samplerecipeapp.domain.util


// the "T" is used to map both Entities and DTOs hence the variable named as T
interface DomainMapper <T, DomainModel> {

    fun mapToDomainModel(model: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T
}