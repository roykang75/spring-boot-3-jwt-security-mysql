package com.alibou.security.policy;

import com.alibou.security.advice.exception.ResourceNotExistException;
import com.alibou.security.apifunc.ApiFunc;
import com.alibou.security.apifunc.ApiFuncRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PolicyService {

    final private PolicyRepository policyRepository;
    final private ApiFuncRepository apiFuncRepository;

    public boolean hasPermission(List<Policy> userPolicies, String apiPath) {
        ApiFunc apiFunc = getApiFunc(apiPath);
        if (apiFunc == null) {
            throw new ResourceNotExistException("API not found: " + apiPath);
        }

        List<Policy> apiPolicies = apiFunc.getPolicies();

        for (Policy userPolicy : userPolicies) {
            for (Policy apiPolicy : apiPolicies) {
                if (policyNamesMatch(userPolicy, apiPolicy)) {
                    return true;
                }
            }
        }

        return false;
    }

    private ApiFunc getApiFunc(String apiPath) {
        Optional<ApiFunc> optApiFunc = apiFuncRepository.findByPath(apiPath);
        return optApiFunc.orElse(null);
    }

    private boolean policyNamesMatch(Policy userPolicy, Policy apiPolicy) {
        return apiPolicy.getName().equalsIgnoreCase(userPolicy.getName());
    }

}
