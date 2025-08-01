package com.erdal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erdal.DTO.UserDTO;
import com.erdal.exeptions.SaloonErrorMessages;
import com.erdal.exeptions.SaloonNotFoundExeption;
import com.erdal.model.Saloon;
import com.erdal.repository.SaloonRepository;
import com.erdal.requests.SaloonRequest;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SaloonServiceImpl implements SaloonService {
	
	private final SaloonRepository saloonRepository;
	
	

	@Override
	public Saloon createSaloon(SaloonRequest saloonRequest, UserDTO userDTO) {
		
		Saloon saloon=new Saloon();
		saloon.setSaloonName(saloonRequest.getSaloonName());
		saloon.setAddress(saloonRequest.getAddress());
		saloon.setCity(saloonRequest.getCity());
		saloon.setEmail(saloonRequest.getEmail());
		saloon.setPhoneNumber(saloonRequest.getPhoneNumber());
		saloon.setImages(saloonRequest.getImages());
		saloon.setOpenTime(saloonRequest.getOpenTime());
		saloon.setCloseTime(saloonRequest.getCloseTime());
		saloon.setOwnerId(userDTO.getId());
		
		Saloon sln=saloonRepository.save(saloon);
		return sln;
	}

	@Override
	public Saloon updateSaloon(SaloonRequest saloonRequest, UserDTO userDTO, Long saloonId) {
		
	Saloon saloon=findSalonBySaloonId(saloonId);
	if (!saloon.getOwnerId().equals(userDTO.getId())) {	
		throw new SaloonNotFoundExeption(SaloonErrorMessages.SALOON_NOT_FOUND);
		
	}
	saloon.setSaloonName(saloonRequest.getSaloonName());
	saloon.setAddress(saloonRequest.getAddress());
	saloon.setCity(saloonRequest.getCity());
	saloon.setEmail(saloonRequest.getEmail());
	saloon.setPhoneNumber(saloonRequest.getPhoneNumber());
	saloon.setImages(saloonRequest.getImages());
	saloon.setOpenTime(saloonRequest.getOpenTime());
	saloon.setCloseTime(saloonRequest.getCloseTime());
	saloon.setOwnerId(userDTO.getId());
		Saloon newSaloon=saloonRepository.save(saloon);
		return newSaloon;
	}

	@Override
	public List<Saloon> getAllSaloon() {
		List<Saloon>saloons=saloonRepository.findAll();
		return saloons;
	}

	@Override
	public Saloon getSaloonByOwnerId(Long ownerId) {
		Saloon saloon=saloonRepository.findByOwnerId(ownerId);
		return saloon;
	}

	@Override
	public Saloon getSaloonById(Long saloonId) {
		Saloon saloon=findSalonBySaloonId(saloonId);
		return saloon;
	}

	@Override
	public List<Saloon> searchSaloonByCityName(String city) {
		List<Saloon> saloons=saloonRepository.searchSaloons(city);
		return saloons;
	}

	@Override
	public void deleteSaloonByOwnerId(Long ownerId) {
		Saloon saloon=saloonRepository.findByOwnerId(ownerId);
		 saloonRepository.delete(saloon);
		 
		
	}
	
	//-----------REUSEABLE METODS------------ 
	
	//-----------findSalonBySaloonid----------
	public Saloon findSalonBySaloonId(Long id) {
		
	Saloon saloon=	saloonRepository.findById(id).orElseThrow(()-> new SaloonNotFoundExeption
			                                                 (String.format(SaloonErrorMessages.SALOON_ID_NOT_FOUND, id) ) );
		
		return saloon;
	}

}
