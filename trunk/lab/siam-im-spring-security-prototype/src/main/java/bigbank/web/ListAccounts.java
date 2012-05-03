package bigbank.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import bigbank.PersonService;

public class ListAccounts implements Controller {

  private PersonService bankService;

  public ListAccounts(PersonService bankService) {
    Assert.notNull(bankService);
    this.bankService = bankService;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // Actual business logic
    ModelAndView mav = new ModelAndView("listAccounts");
    mav.addObject("accounts", bankService.findAccounts());
    return mav;
  }

}
