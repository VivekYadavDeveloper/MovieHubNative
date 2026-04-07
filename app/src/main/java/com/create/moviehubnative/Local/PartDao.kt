package com.create.moviehubnative.Local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.create.moviehubnative.Entities.PartEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface PartDao {

    @Query("SELECT * FROM parts WHERE movieId = :movieId ORDER BY title ASC")
    fun getPartsByMovieId(movieId: String): Flow<List<PartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParts(parts: List<PartEntity>)

    @Update
    suspend fun updatePart(part: PartEntity)

    @Query("DELETE FROM parts WHERE movieId = :movieId")
    suspend fun deletePartsByMovieId(movieId: String)
}