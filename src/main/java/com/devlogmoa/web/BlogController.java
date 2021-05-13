package com.devlogmoa.web;

import com.devlogmoa.repository.BlogDetailRepository;
import com.devlogmoa.web.dto.response.BlogDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogDetailRepository blogDetailRepository;

    @GetMapping("/")
    public String blogs(Model model, Pageable pageable) {
        Page<BlogDetailDto> blogDetails = blogDetailRepository.findAllByOrderByPubDateDesc(pageable)
                                                            .map(BlogDetailDto::new);

        model.addAttribute("blogDetails", blogDetails);

        return "blogList";
    }
}
