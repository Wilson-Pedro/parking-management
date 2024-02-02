package com.wilsonpedro.parking.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public Address save(Address address, Long companyId) {
		validateCep(address.getCep());
		address = prepareToSave(address, companyId);
		return addressRepository.save(address);
	}

	private Address prepareToSave(Address address, Long companyId) {
		Company company = companyService.findById(companyId);
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

	public Address update(Address address, Long id) {
		validateUpdate(address, id);
		return addressRepository.findById(id)
				.map(addressUpdated -> {
					addressUpdated.setId(id);
					addressUpdated.setCep(address.getCep());
					addressUpdated.setStreet(address.getStreet());
					addressUpdated.setNeighborhood(address.getNeighborhood());
					addressUpdated.setCity(address.getCity());
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
	
	private void validateUpdate(Address address, Long id) {
		
		if(addressRepository.existsByCep(address.getCep())) {
			var ads = addressRepository.findByCep(address.getCep()).get();
			if(!Objects.equals(ads.getId(), id))
				throw new ExistingCepException(address.getCep());
		}
	}
}
