package com.javaschool.service.impl.user;

import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.dto.order.AddressDto;
import com.javaschool.dto.order.OrderDto;
import com.javaschool.dto.user.UserDto;
import com.javaschool.dto.user.UserRegistrationDto;
import com.javaschool.dto.user.UserUpdateInfoDto;
import com.javaschool.dto.user.UserUpdatePassDto;
import com.javaschool.entity.User;
import com.javaschool.exception.UserException;
import com.javaschool.mapper.user.UserMapperImpl;
import com.javaschool.repository.user.RoleRepository;
import com.javaschool.repository.user.UserRepository;
import com.javaschool.service.impl.product.BrandServiceImpl;
import com.javaschool.service.order.AddressService;
import com.javaschool.service.order.OrderService;
import com.javaschool.service.user.CardService;
import com.javaschool.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapperImpl userMapper;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final AddressService addressService;
    private final OrderService orderService;
    private final CardService cardService;

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public List<UserDto> getAll() {
        log.info("Get all users");
        List<UserDto> userDtoList = null;
        try {
            userDtoList = userMapper.toDtoList(userRepository.findAll());
        } catch (UserException e) {
            log.error("Error getting all the users", e);
        } catch (Exception e) {
            log.error("Error at UserService.getAll()", e);
        }
        return userDtoList;
    }

    @Override
    @Transactional
    public UserDto getDtoByEmail(String email) {
        log.info("Get user dto by email: " + email);
        UserDto userDto = null;
        try {
            userDto = userMapper.toDto(userRepository.findByEmail(email));
        } catch (UserException e) {
            log.error("Error getting a user by email", e);
        } catch (Exception e) {
            log.error("Error at UserService.getDtoByEmail()", e);
        }
        return userDto;
    }

    @Override
    @Transactional
    public User getByEmail(String email) {
        log.info("Get user by email: " + email);
        User user = null;
        try {
            user = userRepository.findByEmail(email);
        } catch (UserException e) {
            log.error("Error getting a user by email", e);
        } catch (Exception e) {
            log.error("Error at UserService.getByEmail()", e);
        }
        return user;
    }

    @Override
    public UserDto getById(long id) {
        log.info("Get user with id: " + id);
        UserDto userDto = null;
        try {
            userDto = userMapper.toDto(userRepository.findById(id));
        } catch (UserException e) {
            log.error("Error getting a user by id", e);
        } catch (Exception e) {
            log.error("Error at UserService.getById()", e);
        }
        return userDto;
    }

    @Override
    public UserDto toDto(User user) {
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void registerUser(UserRegistrationDto userRegistrationDto) {
        log.info("Registering new user");
        User user = new User();
        try {
            user.setFirstName(userRegistrationDto.getFirstName());
            user.setLastName(userRegistrationDto.getLastName());
            user.setDob(getDate(userRegistrationDto.getDob()));
            user.setEmail(userRegistrationDto.getEmail());
            user.setRoleSet(Collections.singleton(roleRepository.findByName("ROLE_USER")));
            user.setPassword(bCryptPasswordEncoder.encode(userRegistrationDto.getPassword()));
            userRepository.save(user);
        } catch (UserException e) {
            log.error("Error while register user", e);
        } catch (Exception e) {
            log.error("Error at UserService.registerUser()");
        }
    }

    @Override
    public UserUpdateInfoDto getUserUpdateDto(UserDto userDto) {
        return userMapper.toUpdateInfoDto(userDto);
    }


    private LocalDate getDate(String date) {
        String[] nums = date.split("-");
        return LocalDate.of(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), Integer.parseInt(nums[2]));
    }

    @Override
    @Transactional
    public void updateUserInfo(UserUpdateInfoDto userUpdateDto) {
        log.info("Updating user info");
        try {
            User user = userRepository.findById(userUpdateDto.getId());
            user.setFirstName(userUpdateDto.getFirstName());
            user.setLastName(userUpdateDto.getLastName());
            user.setDob(getDate(userUpdateDto.getDob()));
            user.setEmail(userUpdateDto.getEmail());
            Collection<SimpleGrantedAuthority> nowAuthorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
                    .getContext().getAuthentication().getAuthorities();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
                    user.getPassword(),
                    nowAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            userRepository.update(user);
        } catch (UserException e) {
            log.error("Error updating user info", e);
        } catch (Exception e) {
            log.error("Error at UserService.updateUserInfo()", e);
        }
    }

    @Override
    public UserUpdatePassDto getUserUpdatePass(UserDto userDto) {
        return userMapper.toUpdatePassDto(userDto);
    }

    @Override
    @Transactional
    public boolean updateUserPass(UserUpdatePassDto userUpdatePassDto) {
        log.info("Updating user password");
        try {
            User user = userRepository.findById(userUpdatePassDto.getId());
            if (userUpdatePassDto.getPassword().equals(userUpdatePassDto.getConfirmPassword())) {
                user.setPassword(bCryptPasswordEncoder.encode(userUpdatePassDto.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(null);
                userRepository.update(user);
                return true;
            }
        } catch (UserException e) {
            log.error("Error updating user pass", e);
        } catch (Exception e) {
            log.error("Error at UserService.updateUserPass()", e);
        }
        return false;
    }

    @Override
    public boolean checkMatch(UserUpdatePassDto userUpdatePassDto, String password) {
        if (bCryptPasswordEncoder.matches(userUpdatePassDto.getOldPassword(), password)) {
            return true;
        }
        return false;
    }

    @Override
    public String getRegistrationFormController(Model model) {
        log.info("Getting a user registration form");
        model.addAttribute("userRegister", new UserRegistrationDto());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "/registration";
        }
        return "redirect:/";
    }

    @Override
    @Transactional
    public String registerNewUserController(BindingResult bindingResult, UserRegistrationDto userRegistrationDto, Model model) {
        log.info("Retrieving information about a new user to save it");
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (getByEmail(userRegistrationDto.getEmail()) != null) {
            model.addAttribute("userError", "User with this email is already registered");
            return "registration";
        }

        if (!userRegistrationDto.getEmail().equals(userRegistrationDto.getConfirmEmail())) {
            model.addAttribute("emailError", "Email does not match");
            return "registration";
        }

        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword())) {
            model.addAttribute("passwordError", "Password does not match");
            return "registration";
        }

        registerUser(userRegistrationDto);
        return "redirect:/";
    }

    @Override
    public void getAllUserInfoController(Model model) {
        log.info("Getting all user info to display on the model");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        UserDto userFromBd = getDtoByEmail(currentUser);
        model.addAttribute("userInfo", userFromBd);
    }

    @Override
    public void getEditInfoPageController(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        UserDto userFromBd = getDtoByEmail(currentUser);
        model.addAttribute("userForm", getUserUpdateDto(userFromBd));
    }

    @Override
    @Transactional
    public String updateUserInfoController(UserUpdateInfoDto userUpdateDto, BindingResult bindingResult, Model model) {
        log.info("Retrieving information about updated user data to save them");
        if (bindingResult.hasErrors()) {
            return "user-update";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        if (!currentUser.equals(userUpdateDto.getEmail())) {
            if (getByEmail(userUpdateDto.getEmail()) != null) {
                model.addAttribute("userError", "User with this email is already registered");
                return "user-update";
            }
        }
        if (!userUpdateDto.getEmail().equals(userUpdateDto.getConfirmEmail())) {
            model.addAttribute("emailError", "Email does not match");
            return "user-update";
        }
        updateUserInfo(userUpdateDto);
        return "redirect:/profile";
    }

    @Override
    public void getEditPassPageController(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        UserDto userFromBd = getDtoByEmail(currentUser);
        model.addAttribute("userForm", getUserUpdatePass(userFromBd));
    }

    @Override
    @Transactional
    public String updateUserPassController(UserUpdatePassDto userUpdatePassDto, BindingResult bindingResult, Model model) {
        log.info("Retrieving information about updated user password to save them");
        if (bindingResult.hasErrors()) {
            return "user-update-password";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        User userFromBd = getByEmail(currentUser);
        if (!checkMatch(userUpdatePassDto, userFromBd.getPassword())) {
            model.addAttribute("userForm", getUserUpdatePass(toDto(userFromBd)));
            model.addAttribute("oldPassError", "Old password is incorrect");
            return "user-update-password";
        }

        if (!updateUserPass(userUpdatePassDto)) {
            model.addAttribute("userForm", getUserUpdatePass(toDto(userFromBd)));
            model.addAttribute("passwordError", "Password does not match");
            return "user-update-password";
        }
        return "redirect:/login";
    }

    @Override
    public void getAllAddressesController(Model model) {
        log.info("Getting all saved addresses to display on the model");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        UserDto userFromBd = getDtoByEmail(currentUser);
        model.addAttribute("savedAddress", addressService.getAllSaved(userFromBd.getId()));
    }

    @Override
    public void getAllOrdersController(Model model) {
        log.info("Getting all orders for user to display on the model");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        User userFromBd = getByEmail(currentUser);
        model.addAttribute("orders", orderService.findAllDeliveredForUser(userFromBd.getId(), false));
    }

    @Override
    public String getOrderController(long id, Model model) {
        log.info("Getting order to display on the model");
        OrderDto orderDto = orderService.findById(id);
        if(orderDto == null){
            return "404";
        }
        orderService.setOrderProductList(orderDto);
        model.addAttribute("address", addressService.getById(orderDto.getAddress_id()));
        model.addAttribute("order", orderDto);
        model.addAttribute("price", orderService.getAllPriceForOrder(orderDto));
        return "user-order";
    }

    @Override
    public void getPayForOrderPageController(long id, Model model) {
        OrderDto orderDto = orderService.findById(id);
        model.addAttribute("orderForm", orderDto);
        model.addAttribute("savedCard", cardService.getAllByUserId(orderDto.getUser_id()));
        model.addAttribute("cardForm", new CardRegisterDto());
    }

    @Override
    public void getCardsController(Model model) {
        log.info("Getting all saved cards to display on the model");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        UserDto userFromBd = getDtoByEmail(currentUser);
        model.addAttribute("savedCards", cardService.getAllByUserId(userFromBd.getId()));
    }

    @Override
    @Transactional
    public String payOrderByCardController(long id, CardRegisterDto cardRegisterDto, BindingResult bindingResult, String isSaved, Model model) {
        log.info("Retrieving information about payment for order to save them");
        if (bindingResult.hasErrors()) {
            OrderDto orderDto = orderService.findById(id);
            model.addAttribute("orderForm", orderDto);
            model.addAttribute("savedCard", cardService.getAllByUserId(orderDto.getUser_id()));
            return "user-pay-order";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        User userFromBd = getByEmail(currentUser);
        String[] ownerDate = cardRegisterDto.getOwner().split(" ");
        if (ownerDate.length != 2) {
            model.addAttribute("ownerError", "Cardholder data must consist of first and last name");
            return "card-register";
        }
        if (isSaved != null) {
            cardService.addCard(cardRegisterDto, userFromBd);
        }
        orderService.setPaid(id);
        return "redirect:/profile/order/" + id;
    }

    @Override
    public String getPageForEditAddressController(long id, Model model) {
        log.info("Getting a form to update the address");
        AddressDto addressDto = addressService.getById(id);
        if(addressDto == null){
            return "404";
        }
        model.addAttribute("addressForm", addressDto);
        return "user-address-edit";
    }

    @Override
    @Transactional
    public String editAddressController(AddressDto addressDto, BindingResult bindingResult) {
        log.info("Retrieving information about updated user address to save them");
        if (bindingResult.hasErrors()) {
            return "user-address-edit";
        }
        if (orderService.getOrdersByAddressId(addressDto.getId()).size() == 0) {
            addressService.updateAddress(addressDto);
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUser = authentication.getName();
            User userFromBd = getByEmail(currentUser);
            addressService.updateSavedAddress(addressDto.getId());
            addressService.addUpdateAddress(addressDto, userFromBd);
        }
        return "redirect:/profile/addresses";
    }

    @Override
    public void getDeliveredOrdersController(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        UserDto userFromBd = getDtoByEmail(currentUser);
        model.addAttribute("orders", orderService.findAllDeliveredForUser(userFromBd.getId(), true));
    }
}
