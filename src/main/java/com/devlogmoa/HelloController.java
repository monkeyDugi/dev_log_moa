package com.devlogmoa;

import com.devlogmoa.domain.BlogDetail;
import com.devlogmoa.repository.BlogDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HelloController {

    private final BlogDetailRepository blogDetailRepository;

    @GetMapping("hello")
    public String hello(Model model) {
        List<BlogDetail> blogDetails = blogDetailRepository.findAll();

        model.addAttribute("blogs", blogDetails);

        return "blogList";
    }
}
