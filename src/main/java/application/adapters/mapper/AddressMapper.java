package application.adapters.mapper;

import application.adapters.persistence.entity.AddressEntity;
import application.adapters.web.presenter.UserAddressDTO;
import application.domain.Address;

import java.util.List;

public interface AddressMapper {
    UserAddressDTO addressToUserAddressDTO(Address address);
    Address userAddressDTOToAddress(UserAddressDTO userAddressDTO);
    AddressEntity addressToAddressEntity (Address address);
    Address addressEntityToAddress(AddressEntity address);
    List<UserAddressDTO> listAddressTolistUserAddressDTO(List<Address> addressList);
    List<Address> listUserAddressDTOTolistAddress(List<UserAddressDTO> userAddressDTOListToListAddress);
    List<AddressEntity> listAddressToAddressEntity (List<Address> addressList);
    List<Address> listaddressEntityToLisAddress(List<AddressEntity> addressEntityList);
}
