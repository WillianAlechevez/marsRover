package com.estudoapi.MarsRoverApi.repository;

import com.estudoapi.MarsRoverApi.dto.HomeDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferencesRepository extends JpaRepository<HomeDto, Long> {

//    @Query("select dto from HomeDto dto where userId = :userId")
//    @Query(value="select * from mars_api_preferences where user_id = :userId", nativeQuery = true)
    HomeDto findByUserId(Long userId);
}
