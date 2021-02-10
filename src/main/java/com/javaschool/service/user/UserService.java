package com.javaschool.service.user;

import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.dto.order.AddressDto;
import com.javaschool.dto.user.UserDto;
import com.javaschool.dto.user.UserRegistrationDto;
import com.javaschool.dto.user.UserUpdateInfoDto;
import com.javaschool.dto.user.UserUpdatePassDto;
import com.javaschool.entity.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto getDtoByEmail(String email);

    User getByEmail(String email);

    UserDto getById(long id);

    User getUserById(long id);

    UserDto toDto(User user);

    void registerUser(UserRegistrationDto userRegistrationDto);

    UserUpdateInfoDto getUserUpdateDto(UserDto userDto);

    void updateUserInfo(UserUpdateInfoDto userUpdateDto);

    UserUpdatePassDto getUserUpdatePass(UserDto userDto);

    boolean updateUserPass(UserUpdatePassDto userUpdatePassDto);

    boolean checkMatch(UserUpdatePassDto userUpdatePassDto, String password);

    String getRegistrationFormController(Model model);

    String registerNewUserController(BindingResult bindingResult, UserRegistrationDto userRegistrationDto, Model model);

    void getAllUserInfoController(Model model);

    void getEditInfoPageController(Model model);

    String updateUserInfoController(UserUpdateInfoDto userUpdateDto, BindingResult bindingResult, Model model);

    void getEditPassPageController(Model model);

    String updateUserPassController(UserUpdatePassDto userUpdatePassDto, BindingResult bindingResult, Model model);

    void getAllAddressesController(Model model);

    void getAllOrdersController(Model model);

    void getOrderController(long id, Model model);

    void getPayForOrderPageController(long id, Model model);

    void getCardsController(Model model);

    String payOrderByCardController(long id, CardRegisterDto cardRegisterDto, BindingResult bindingResult, String isSaved, Model model);

    void getPageForEditAddressController(long id, Model model);

    String editAddressController(AddressDto addressDto, BindingResult bindingResult);

    void getDeliveredOrdersController(Model model);
}
