package com.javaschool.service.user;

import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.dto.order.AddressDto;
import com.javaschool.dto.user.UserDto;
import com.javaschool.dto.user.UserRegistrationDto;
import com.javaschool.dto.user.UserUpdateInfoDto;
import com.javaschool.dto.user.UserUpdatePassDto;
import com.javaschool.entity.User;
import com.javaschool.exception.ProductException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.awt.*;
import java.util.List;

/**
 * The interface User service.
 *
 * @author Marat Ialyshev
 */
public interface UserService {

    /**
     * Find all list.
     * @return the list
     */
    List<UserDto> getAll();

    /**
     * Find by email user DTO entity.
     *
     * @param email the email
     * @return the user DTO entity
     */
    UserDto getDtoByEmail(String email);

    /**
     * Find by email user entity.
     *
     * @param email the email
     * @return the user entity
     */
    User getByEmail(String email);

    /**
     * Find by id user entity.
     *
     * @param id the id
     * @return the user entity
     */
    UserDto getById(long id);

    /**
     * Entity to dto transfer.
     *
     * @param user the user entity
     * @return the user DTO entity
     */
    UserDto toDto(User user);

    /**
     * Register user entity.
     *
     * @param userRegistrationDto the user entity
     */
    void registerUser(UserRegistrationDto userRegistrationDto);

    /**
     * Getting the DTO of an entity to update info.
     *
     * @param userDto the user entity
     * @return the user DTO entity
     */
    UserUpdateInfoDto getUserUpdateDto(UserDto userDto);

    /**
     * Update user info.
     *
     * @param userUpdateDto the user entity with updates
     */
    void updateUserInfo(UserUpdateInfoDto userUpdateDto);

    /**
     * Getting the DTO of an entity to update password.
     *
     * @param userDto the user entity
     * @return the user DTO entity
     */
    UserUpdatePassDto getUserUpdatePass(UserDto userDto);

    /**
     * Update user password.
     *
     * @param userUpdatePassDto the user entity with updates
     * @return Update result
     */
    boolean updateUserPass(UserUpdatePassDto userUpdatePassDto);

    /**
     * Password matching check.
     *
     * @param userUpdatePassDto the user entity
     * @param password new password
     * @return Result of checking
     */
    boolean checkMatch(UserUpdatePassDto userUpdatePassDto, String password);

    /**
     * Controller for receiving registration form.
     *
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String getRegistrationFormController(Model model);

    /**
     * Controller for registering new users.
     *
     * @param bindingResult bindingResult
     * @param userRegistrationDto user entity
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String registerNewUserController(BindingResult bindingResult, UserRegistrationDto userRegistrationDto, Model model);

    /**
     * Controller for getting all information about the user.
     *
     * @param model the model on which we put the required data
     */
    void getAllUserInfoController(Model model);

    /**
     * Controller to get the user information update page.
     *
     * @param model the model on which we put the required data
     */
    void getEditInfoPageController(Model model);

    /**
     * Controller for updating user information.
     *
     * @param bindingResult bindingResult
     * @param userUpdateDto user entity
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String updateUserInfoController(UserUpdateInfoDto userUpdateDto, BindingResult bindingResult, Model model);

    /**
     * Controller to get the user password update page.
     *
     * @param model the model on which we put the required data
     */
    void getEditPassPageController(Model model);

    /**
     * Controller for updating user password.
     *
     * @param bindingResult bindingResult
     * @param userUpdatePassDto user entity
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String updateUserPassController(UserUpdatePassDto userUpdatePassDto, BindingResult bindingResult, Model model);

    /**
     * Controller for retrieving all saved user addresses.
     *
     * @param model the model on which we put the required data
     */
    void getAllAddressesController(Model model);

    /**
     * Controller for receiving all user orders.
     *
     * @param model the model on which we put the required data
     */
    void getAllOrdersController(Model model);

    /**
     * Controller for ordering information.
     *
     * @param id the order id
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String getOrderController(long id, Model model);

    /**
     * Controller for receiving the order payment page.
     *
     * @param id the order id
     * @param model the model on which we put the required data
     */
    void getPayForOrderPageController(long id, Model model);

    /**
     * Controller for retrieving all saved user cards.
     *
     * @param model the model on which we put the required data
     */
    void getCardsController(Model model);

    /**
     * Controller for order payment with card.
     *
     * @param cardRegisterDto the card entity
     * @param bindingResult bindingResult
     * @param id the order id
     * @param isSaved card save option
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String payOrderByCardController(long id, CardRegisterDto cardRegisterDto, BindingResult bindingResult, String isSaved, Model model);

    /**
     * Controller for getting the address edit page.
     *
     * @param id the address id
     * @param model the model on which we put the required data
     *
     * @return the title of the html page to display
     */
    String getPageForEditAddressController(long id, Model model);

    /**
     * Controller for editing user address.
     *
     * @param addressDto the address entity with updates
     * @param bindingResult bindingResult
     *
     * @return the title of the html page to display
     */
    String editAddressController(AddressDto addressDto, BindingResult bindingResult);

    /**
     * Controller for receiving a page with completed orders.
     *
     * @param model the model on which we put the required data
     */
    void getDeliveredOrdersController(Model model);
}
