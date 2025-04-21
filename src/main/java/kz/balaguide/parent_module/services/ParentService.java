package kz.balaguide.parent_module.services;

import kz.balaguide.common_module.core.entities.BankCard;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Parent;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ParentNotFoundException;
import kz.balaguide.parent_module.dtos.CreateChildRequest;
import kz.balaguide.parent_module.dtos.CreateParentRequest;
import kz.balaguide.parent_module.dtos.UpdateParentRequest;

import java.util.List;

public interface ParentService {

    Parent createParentAndSave(CreateParentRequest createParentRequest);

    Child addChild(Long parentId, CreateChildRequest createChildRequest);

    Parent findByPhoneNumber(String phoneNumber);

    Parent findParentById(Long id);

    boolean removeChild(Long parentId, Long childId);

    List<Child> getMyChildren(Long parentId);

    String addBalance(Long parentId, Integer amountOfMoney, BankCard bankCard) throws ParentNotFoundException;

    Parent updateParent(Long parentId, UpdateParentRequest updatedParent) throws ParentNotFoundException;

}
