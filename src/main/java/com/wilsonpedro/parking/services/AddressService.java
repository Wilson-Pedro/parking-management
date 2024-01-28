package com.wilsonpedro.parking.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilsonpedro.parking.dtos.AddressDTO;
import com.wilsonpedro.parking.exceptions.ExistingCepException;
import com.wilsonpedro.parking.exceptions.NotFoundException;
import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.repositories.AddressRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private CompanyService companyService;
	
	public Address save(AddressDTO addressDto) {
		validateCep(addressDto.getCep());
		Address address = toEntity(addressDto);
		return addressRepository.save(address);
	}

	private Address toEntity(AddressDTO addressDto) {
		Company company = companyService.findById(addressDto.getCompanyId());
		Address address = new Address(addressDto);
		address.setCompany(company);
		return address;
	}

	public List<Address> findAll() {
		return addressRepository.findAll();
	}

	public Address findById(Long id) {
		return addressRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(id));
	}

	public Address update(AddressDTO addressDTO, Long id) {
		validateUpdate(addressDTO, id);
		return addressRepository.findById(id)
				.map(addressUpdated -> {
					addressUpdated.setId(id);
					addressUpdated.setCep(addressDTO.getCep());
					addressUpdated.setStreet(addressDTO.getStreet());
					addressUpdated.setNeighborhood(addressDTO.getNeighborhood());
					addressUpdated.setCity(addressDTO.getCity());
					return addressRepository.save(addressUpdated);
				}).orElseThrow(() -> new NotFoundException(id));
	}

	public void delete(Long id) {
		addressRepository.delete(addressRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(id)));
	}
	
	private void validateCep(String cep) {
		if(addressRepository.existsByCep(cep))
			throw new ExistingCepException(cep);
	}
	
	private void validateUpdate(AddressDTO addressDTO, Long id) {
		
		if(addressRepository.existsByCep(addressDTO.getCep())) {
			var address = addressRepository.findByCep(addressDTO.getCep()).get();
			if(!Objects.equals(address.getId(), id))
				throw new ExistingCepException(addressDTO.getCep());
		}
		
	}
}
