//package com.example.bingewatch.repository
//
//import com.example.bingewatch.db.MovieDao
//import com.example.bingewatch.models.Movie
//
//class FavesRepositoryImpl(private val movieDao: MovieDao) : FavesRepository {
//    override suspend fun getAllMovies(): List<Movie> {
//        return movieDao.getAllMovies()
//    }
//
//    override suspend fun insertMovie(movie: Movie) {
//        movieDao.insertMovie(movie)
//    }
//
//    override suspend fun deleteMovieById(movieId: Long) {
//        movieDao.deleteMovieById(movieId)
//    }
//
//    override suspend fun deleteAllMovies() {
//        movieDao.deleteAllMovies()
//    }
//}
