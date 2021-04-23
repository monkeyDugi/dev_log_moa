package com.devlogmoa.web;

import com.devlogmoa.domain.BlogDetail;
import com.devlogmoa.repository.BlogDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogDetailRepository blogDetailRepository;

    @GetMapping("/")
    public String blogs(Model model) {
        List<BlogDetail> blogDetails = blogDetailRepository.findAll();

        model.addAttribute("blogDetails", blogDetails);

        return "blogList";
    }
}
