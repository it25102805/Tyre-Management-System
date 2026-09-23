package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.MaterialType;
import lk.asityre.tyrerebuild.webapp.repository.MaterialTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialTypeService {

    @Autowired
    private MaterialTypeRepository materialTypeRepository;

    public List<MaterialType> getAllMaterialTypes() {
        return materialTypeRepository.findAll();
    }

    public MaterialType getMaterialTypeById(Integer id) {
        return materialTypeRepository.findById(id).orElse(null);
    }

    public MaterialType saveMaterialType(MaterialType materialType) {
        return materialTypeRepository.save(materialType);
    }

    public void deleteMaterialType(Integer id) {
        materialTypeRepository.deleteById(id);
    }

    public MaterialType updateMaterialType(Integer id, MaterialType updatedType) {
        MaterialType existingType = materialTypeRepository.findById(id).orElse(null);
        if (existingType != null) {
            existingType.setMaterialName(updatedType.getMaterialName());
            existingType.setUnit(updatedType.getUnit());
            existingType.setReorderLevel(updatedType.getReorderLevel());
            return materialTypeRepository.save(existingType);
        }
        return null;
    }
}