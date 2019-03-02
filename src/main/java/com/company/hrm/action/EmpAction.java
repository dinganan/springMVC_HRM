package com.company.hrm.action;

import com.company.hrm.common.ResResult;
import com.company.hrm.common.SpringIOC;
import com.company.hrm.dao.pojo.Emp;
import com.company.hrm.service.iService.IEmpService;
import com.company.hrm.service.impl.EmpServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class EmpAction {
    @Autowired
    private EmpServiceImpl empService;
    @RequestMapping("EmpDeleteServlet.do")
    public void EmpDelete(Integer empno){
        Emp emp = (Emp) SpringIOC.getCtx().getBean("emp");
        emp.setEmpno(empno);
        empService.delete(emp);
    }
    @RequestMapping("EmpFindAllServlet.do")
    public @ResponseBody ResResult EmpFindAll(HttpSession session){
        ResResult<List<Emp>> result = null;
        if (session.getAttribute("username") != null) {
            List<Emp> empList = new ArrayList<Emp>();
            empList = empService.findAll();
            if (!empList.isEmpty() && empList.size() > 0) {
                result = ResResult.success("find all success", empList);
            }else {
                result = ResResult.error(404, "no data");
            }
        }else {
            result = ResResult.error(301, "have not login!");
        }
        return result;
    }
    @RequestMapping("EmpFindByIdServlet.do")
    public @ResponseBody ResResult EmpFindById(Integer empno){
        Emp e = empService.findById(empno);
        ResResult<List<Emp>> result = null;
        List<Emp> empList = new ArrayList<Emp>();
        empList.add(e);
        if (!empList.isEmpty() && empList.size() > 0) {
            result = ResResult.success("find by id success", empList);
        }else {
            result = ResResult.error(404, "no data");
        }
       return result;
    }
    @RequestMapping("EmpFindByNameServlet.do")
    public @ResponseBody ResResult EmpFindByName(String ename){
        List<Emp> empList = empService.findByName(ename);
        ResResult<List<Emp>> result = null;
        if (!empList.isEmpty() && empList.size() > 0) {
            result = ResResult.success("find by name success", empList);
        }else {
            result = ResResult.error(404, "no data");
        }
        return result;
    }
    @RequestMapping("EmpSaveServlet.do")
    public @ResponseBody ResResult EmpSave(Emp emp) throws ParseException {
        String msg = empService.save(emp);
        boolean flag2 = msg.equals("success");
        ResResult result = flag2?ResResult.success():ResResult.error(500,"emp insert error");
        return result;
    }
    @RequestMapping("EmpUpdateServlet.do")
    public @ResponseBody ResResult EmpUpdate(Emp emp) throws ParseException {
        String msg = empService.update(emp);
        boolean flag2 = msg.equals("success");
        ResResult result = flag2?ResResult.success():ResResult.error(500,"emp update error");
        return result;
    }
}
