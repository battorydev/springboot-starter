package io.github.battorydev.springboot.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.battorydev.springboot.demo.model.JobJsonObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class JobDataControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenTitleAndSalaryField_whenGetJobData_showTitleAndSalaryOnly() throws Exception {
        String uri = "/job_data?fields=title,salary";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String contentType = mvcResult.getResponse().getContentType();
        assertEquals("application/json", contentType);

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JobJsonObject[] actual = mapper.readValue(content, JobJsonObject[].class);

        assertTrue(actual.length > 0);
        assertFalse(actual[0].getTitle().isEmpty());
        assertFalse(actual[0].getSalary().isEmpty());
        assertTrue(actual[0].getGender() == null);
        assertTrue(actual[0].getComment() == null);
        assertTrue(actual[0].getEmployer() == null);
        assertTrue(actual[0].getAnnualBonus() == null);
        assertTrue(actual[0].getAnnualStock() == null);
        assertTrue(actual[0].getLocation() == null);
        assertTrue(actual[0].getSigningBonus() == null);
        assertTrue(actual[0].getTimestamp() == null);
        assertTrue(actual[0].getYearsAtEmployer() == null);
        assertTrue(actual[0].getYearsOfExp() == null);
    }

    @Test
    void givenConditionSalaryGraterThan_whenGetJobData_returnJobSalaryGreaterThan() throws Exception {
        String uri = "/job_data?condition=salary>=10000000";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String contentType = mvcResult.getResponse().getContentType();
        assertEquals("application/json", contentType);

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JobJsonObject[] actual = mapper.readValue(content, JobJsonObject[].class);

        assertTrue(actual.length > 0);
        for (JobJsonObject act : actual) {
            assertTrue(Double.parseDouble(act.getSalary()) >= 10000000);
        }
    }

    @Test
    void givenSortAndSortType_whenGetJobData_returnSortedData() throws Exception {
        String uri = "/job_data?sort=title&sort_type=DESC";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String contentType = mvcResult.getResponse().getContentType();
        assertEquals("application/json", contentType);

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JobJsonObject[] actual = mapper.readValue(content, JobJsonObject[].class);

        assertTrue(actual.length > 0);
        for (int i = 1; i < actual.length; i++) {
            assertTrue(actual[i - 1].getTitle().compareToIgnoreCase(actual[i].getTitle()) >= 0);
        }
    }
}
