package com.tcs.Practice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.Practice.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Long> {
	Optional<Address> findByCityAndStreet(String city, String street);
}
