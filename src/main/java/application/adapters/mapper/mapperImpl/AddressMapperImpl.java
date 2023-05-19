package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.AddressMapper;
import application.adapters.persistence.entity.AddressEntity;
import application.adapters.web.presenter.UserAddressDTO;
import application.domain.Address;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressMapperImpl implements AddressMapper {
    @Override
    public UserAddressDTO addressToUserAddressDTO(Address address) {
        return new UserAddressDTO(address.getId(),address.getStreet(),address.getCity(),address.getState());
    }

    @Override
    public Address userAddressDTOToAddress(UserAddressDTO userAddressDTO) {
        return new Address(userAddressDTO.getId(),userAddressDTO.getStreet(),userAddressDTO.getCity(),userAddressDTO.getState());
    }

    @Override
    public AddressEntity addressToAddressEntity(Address address) {
        return new AddressEntity(address.getId(),address.getStreet(),address.getCity(),address.getState());
    }

    @Override
    public Address addressEntityToAddress(AddressEntity addressEntity) {
        return new Address(addressEntity.getId(),addressEntity.getStreet(),addressEntity.getCity(),addressEntity.getState());
    }

    @Override
    public List<UserAddressDTO> listAddressTolistUserAddressDTO(List<Address> addressList) {
        List<UserAddressDTO> userAddressDTOList = new ArrayList<>();
        for (Address address : addressList)
            userAddressDTOList.add(addressToUserAddressDTO(address));

        return userAddressDTOList;
    }

    public List<Address> listUserAddressDTOTolistAddress(List<UserAddressDTO> userAddressDTOListToListAddress) {
        List<Address> addressList = new ArrayList<>();
        if (userAddressDTOListToListAddress != null) {
            for (UserAddressDTO userAddressDTO : userAddressDTOListToListAddress) {
                addressList.add(userAddressDTOToAddress(userAddressDTO));
            }
        }
        return addressList;
    }

    @Override
    public List<AddressEntity> listAddressToAddressEntity(List<Address> addressList) {
        List <AddressEntity> addressEntityList=new ArrayList<>();
        for(Address address:addressList){
            addressEntityList.add(this.addressToAddressEntity(address));
        }
        return addressEntityList;
    }

    @Override
    public List<Address> listaddressEntityToLisAddress(List<AddressEntity> addressEntityList) {
        List <Address> addressList =new ArrayList<>();
        for(AddressEntity addressEntity:addressEntityList){
            addressList.add(this.addressEntityToAddress(addressEntity));
        }
        return addressList;
    }
}
