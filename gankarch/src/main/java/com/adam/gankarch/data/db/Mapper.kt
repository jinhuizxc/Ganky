package com.adam.gankarch.data.db

/**
 * Created by yu on 2017/10/30.
 */
interface Mapper<F, T> {
    fun map(f: F): T
    fun mapRevert(t: T): F
}