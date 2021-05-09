package com.devlogmoa.web;

import com.devlogmoa.repository.BlogDetailRepository;
import com.devlogmoa.web.dto.response.BlogDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogDetailRepository blogDetailRepository;

    @GetMapping("/")
    public String blogs(Model model) {
        List<BlogDetailDto> blogDetails = blogDetailRepository.findTop20ByOrderByPubDateDesc()
                .stream()
                .map(BlogDetailDto::new)
                .collect(Collectors.toList());

        model.addAttribute("blogDetails", blogDetails);

        return "blogList";
    }
}
