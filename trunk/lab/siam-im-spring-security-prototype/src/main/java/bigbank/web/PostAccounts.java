package bigbank.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import bigbank.Person;
import bigbank.PersonService;

public class PostAccounts implements Controller {

    private PersonService bankService;

    public PostAccounts(PersonService bankService) {
        Assert.notNull(bankService);
        this.bankService = bankService;
    }

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Security check (this is unnecessary if Spring Security is performing the authorization)
//        if (!request.isUserInRole("ROLE_TELLER")) {
//            throw new AccessDeniedException("You must be a teller to post transactions (Spring Security message)");
//        }

        // Actual business logic
        Long id = ServletRequestUtils.getRequiredLongParameter(request, "id");
        Double amount = ServletRequestUtils.getRequiredDoubleParameter(request, "amount");
        Person a = bankService.readAccount(id);
        bankService.post(a, amount);

        return new ModelAndView("redirect:listAccounts.html");
    }

}
