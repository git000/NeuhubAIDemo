package neuhub;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import neuhub.properties.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

/**
 * {@link NeuhubAIDemoTester#asr()} 语音识别接口
 * {@link NeuhubAIDemoTester#comment()} 评论观点抽取接口
 * {@link NeuhubAIDemoTester#faceCompare()} 人脸对比接口
 * {@link NeuhubAIDemoTester#facePropV1()} 人脸检测与属性分析接口
 * {@link NeuhubAIDemoTester#detectHacknessV1()} 人脸活体检测接口
 * {@link NeuhubAIDemoTester#food()} 菜品识别接口
 * {@link NeuhubAIDemoTester#humanDetect()} 人体检测接口
 * {@link NeuhubAIDemoTester#ocrIdCard()} 身份证识别接口
 * {@link NeuhubAIDemoTester#ocrInvoice()} 增值税发票识别接口
 * {@link NeuhubAIDemoTester#politiciansRecognition()} 特定人物识别接口
 * {@link NeuhubAIDemoTester#lexer()} 词法分析接口
 * {@link NeuhubAIDemoTester#poseEstimation()} 人体关键点检测接口
 * {@link NeuhubAIDemoTester#searchFace()} 人脸搜索接口
 * {@link NeuhubAIDemoTester#faceGroupCreate()} 创建人脸分组接口
 *  * * * {@link NeuhubAIDemoTester#groupFetchV1()} 获取分组列表
 * {@link NeuhubAIDemoTester#faceGroupDelete()} 删除人脸分组接口
 * {@link NeuhubAIDemoTester#faceCreate()} 创建人脸接口
 * {@link NeuhubAIDemoTester#faceDelete()} 删除人脸接口
 * {@link NeuhubAIDemoTester#getFaceGroupList()} 获取人脸分组列表接口
 * {@link NeuhubAIDemoTester#selfieSegmentation()} 自拍人像抠图接口
 * {@link NeuhubAIDemoTester#sentiment()} 情感分析接口
 * {@link NeuhubAIDemoTester#sexyGet()} 智能鉴黄(GET请求)接口
 * {@link NeuhubAIDemoTester#localCvImage()} 智能鉴黄(POST请求)接口
 * {@link NeuhubAIDemoTester#similarity()} 短文本相似度接口
 * {@link NeuhubAIDemoTester#snapShop()} 拍照购接口
 * {@link NeuhubAIDemoTester#textClassification()} 文本分类接口
 * {@link NeuhubAIDemoTester#systax()} 句法分析接口
 * {@link NeuhubAIDemoTester#tts()} 语音合成接口
 * {@link NeuhubAIDemoTester#ocrUniversal()} 通用文字识别接口
 * {@link NeuhubAIDemoTester#ocrVehicleRecognition()} 行驶证识别接口
 * {@link NeuhubAIDemoTester#ocrBankcard()} 银行卡识别接口
 * {@link NeuhubAIDemoTester#ocrBusiness()} 营业执照识别接口
 * {@link NeuhubAIDemoTester#garbageImageSearch()} 垃圾分类图像识别接口
 * {@link NeuhubAIDemoTester#garbageVoiceSearch()} 垃圾分类语音识别接口
 * {@link NeuhubAIDemoTester#garbageTextSearch()} 垃圾分类文本识别接口
 * {@link NeuhubAIDemoTester#humanParsing()} 细粒度人像分割接口
 * {@link NeuhubAIDemoTester#personreid()} 行人重识别接口
 * {@link NeuhubAIDemoTester#image_recognition_product()} 通用商品识别接口
 * {@link NeuhubAIDemoTester#vehicleDetection()} 车辆检测接口
 * {@link NeuhubAIDemoTester#imageSearchIndex()} 通用图片搜索图片入库
 * {@link NeuhubAIDemoTester#imageSearchTask()} 通用图片搜索任务状态查询
 * {@link NeuhubAIDemoTester#imageSearch()} 通用图片搜索
 * {@link NeuhubAIDemoTester#universalForPesticide()} 化肥农药袋子识别
 * {@link NeuhubAIDemoTester#extract_img_colors()} 颜色识别
 * {@link NeuhubAIDemoTester#detect_shelf_product()} 货架商品检测
 * {@link NeuhubAIDemoTester#recommend_outfits()} 搭配生成
 * {@link NeuhubAIDemoTester#product_image_create_product()} 商品图片搜索_创建商品
 * {@link NeuhubAIDemoTester#product_image_add()} 商品图片搜索_商品图入库
 * {@link NeuhubAIDemoTester#product_image_status()} 商品图片搜索_入库状态查询
 * {@link NeuhubAIDemoTester#product_image_search()} 商品图片搜索_商品图片搜索
 * {@link NeuhubAIDemoTester#product_image_info()} 商品图片搜索_商品图片信息查询
 * {@link NeuhubAIDemoTester#product_image_del_image()} 商品图片搜索_商品图片删除
 * {@link NeuhubAIDemoTester#product_image_del_product()} 商品图片搜索_商品删除
 * {@link NeuhubAIDemoTester#product_image_del_collection()} 商品图片搜索_商品库删除
 * {@link NeuhubAIDemoTester#medical_material_recognize()} 疫情物资识别
 * {@link NeuhubAIDemoTester#marketSegmentationAnalyse()} 细分市场划分
 * {@link NeuhubAIDemoTester#brandCompeteAnalyse()} 品牌竞争力分析
 * {@link NeuhubAIDemoTester#productUpgrade()} 老品升级
 * {@link NeuhubAIDemoTester#productCustom()} 新品定制
 * {@link NeuhubAIDemoTester#compete_analysis()} 商品竞争力分析
 * {@link NeuhubAIDemoTester#nmt()} 机器翻译
 * {@link NeuhubAIDemoTester#similar()} 词义相似度
 * {@link NeuhubAIDemoTester#word_vector()} 词向量
 * {@link NeuhubAIDemoTester#phrase_extract()} 短语挖掘
 * {@link NeuhubAIDemoTester#create_article()} 智能写作
 * {@link NeuhubAIDemoTester#chatbot()} 闲聊
 * {@link NeuhubAIDemoTester#language_smooth()} 文本流畅度
 * {@link NeuhubAIDemoTester#corpus_synonyms()} 同义词
 * {@link NeuhubAIDemoTester#corpus_aggregation()} 聚类
 * {@link NeuhubAIDemoTester#language_cloze()} 语言模型填空
 * {@link NeuhubAIDemoTester#hotwords()} 新闻热词抽取
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NeuhubAIDemoApplication.class)
public class NeuhubAIDemoTester {

    private Logger logger = LoggerFactory.getLogger(NeuhubAIDemoTester.class);
    private RestTemplate restTemplate;
    private ClientCredentialsResourceDetails clientCredentialsResourceDetails;

    /**
     * 这不是一个普通的RestTemplate，而是引用的OAuth2RestTemplate
     *
     * @param restTemplate
     */
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Autowired
    public void setClientCredentialsResourceDetails(ClientCredentialsResourceDetails clientCredentialsResourceDetails) {
        this.clientCredentialsResourceDetails = clientCredentialsResourceDetails;
    }

    /**
     * 调用的api网关地址
     */
    @Value("${gateway.url}")
    private String gatewayUrl;

    /**
     * 测试时使用的图片位置，在配置文件中进行修改
     */
    @Value("${neuhub.picture}")
    private String picture;

    /**
     * 人脸对比接口测试时使用的图片位置，在配置文件中进行修改
     */
    @Value("${neuhub.pictureCompare}")
    private String pictureCompare;

    /**
     * 测试时使用的文本内容，在配置文件中进行修改
     */
    @Value("${neuhub.comment}")
    private String comment;

    /**
     * 测试短文本相似度接口的文本内容，在配置文件中进行修改
     */
    @Value("${neuhub.commentCompare}")
    private String commentCompare;

    /**
     * 测试之前进行环境检查，确保输入正确的clientId和clientSecret
     */
//    @Before
//    public void checkEnvironment() {
//        int clientIdLength = 32;
//        int clientSecretLength = 10;
//        if (clientCredentialsResourceDetails.getClientId() == null || clientCredentialsResourceDetails.getClientId().length() != clientIdLength) {
//            logger.error("clientId有误，请重新填写");
//            System.exit(1);
//        }
//
//        if (clientCredentialsResourceDetails.getClientSecret() == null || clientCredentialsResourceDetails.getClientSecret().length() != clientSecretLength) {
//            logger.error("clientSecret有误，请重新填写");
//            System.exit(1);
//        }
//    }
    @Test
    public void humanDetect() {
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        //以下参数仅为示例值
        String requestUrl = gatewayUrl + "/neuhub/human_detect";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void poseEstimation() {
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        //muti_det为单人姿态(1)或多人姿态(2)
        int muti_det = 1;
        String requestUrl = gatewayUrl + "/neuhub/pose_estimation?muti_det={muti_det}";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class, muti_det);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void detectHacknessV1() {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);
        String value = String.format("imageBase64=%s", encodedText);
        HttpEntity<String> requestEntity = new HttpEntity<>(value);
        String requestUrl = gatewayUrl + "/neuhub/detectHacknessV1";

        //TODO  增加JSON参数，此接口暂时无法调通
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isVisual", true);
        map.put("imgBase64Visual", encodedText);
        map.put("imgBase64Nir", encodedText);

        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class, map);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void faceCompare() {
        byte[] face1Data = dataBinary(picture);
        byte[] face2Data = dataBinary(pictureCompare);
        String face1 = imageBase64(face1Data);
        String face2 = imageBase64(face2Data);
        String param = String.format("imageBase64_1=%s&imageBase64_2=%s", face1, face2);
        HttpEntity<String> requestEntity = new HttpEntity<>(param);
        String requestUrl = gatewayUrl + "/neuhub/face_compare";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void facePropV1() {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);
        String value = String.format("imageBase64=%s", encodedText);
        HttpEntity<String> requestEntity = new HttpEntity<>(value);

        //TODO 此接口增加请求体参数，暂时无法调通
        Map<String, Object> map = new HashMap<>();
        map.put("imgBase64", encodedText);
        String requestUrl = gatewayUrl + "/neuhub/facePropV1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class, map);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 创建人脸分组
     */
    @Test
    public void faceGroupCreate() {
        /**
         * groupName为用户创建分组的名称，根据分组名称完成创建后可以获得groupId，
         * 通过{@link NeuhubAIDemoTester#getFaceGroupList()} 查看分组信息
         */
        String groupName = "zhoujiaweiTest";
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, null);
        String requestUrl = gatewayUrl + "/neuhub/groupCreateV1?groupName={groupName}";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class, groupName);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }


    /**
     * 获取分组列表
     */
    @Test
    public void groupFetchV1() {
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, null);
        String requestUrl = gatewayUrl + "/neuhub/groupFetchV1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     *  创建人脸接口
     */
    @Test
    public void faceCreate() {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);

        //调用 faceGroupCreate() 接口创建分组ID
        String groupId = "5f9698af6feeeab9f228b8aa";
        Map<String,Object>map = new HashMap();
        map.put("groupId",groupId);
        map.put("imgBase64",encodedText);

        //调用创建分组接口
        HttpEntity<Object> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/faceCreateV1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void faceDelete() {
        /**
         * groupId  需要调接口去创建 {@link NeuhubAIDemoTester#faceGroupCreate()}
         * outerId  待删除的人脸图片的id值
         */
        String groupId = "bfaca672-d954-4207-8e35-26aeeb276d71";
        String outerId = "0726testFace1";
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, null);
        String requestUrl = gatewayUrl + "/neuhub/faceDelete?groupId=c0a0ed2b-a355-48c7-a4f7-c702fda26308&groupName=&outerId=0726testFace1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void faceGroupDelete() {
        /**
         * groupId 为待删除的分组ID
         */
        String groupId = "5249f5d4-96ad-46b5-8e88-1e51be2d20c8";
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, null);
        String requestUrl = gatewayUrl + "/neuhub/faceGroupDelete?groupId={groupId}";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class, groupId);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void getFaceGroupList() {
        /**
         * start为查询起始位置，length为从查询起始位置开始查询的长度
         */
        int start = 0;
        int length = 5;
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, null);
        String requestUrl = gatewayUrl + "/neuhub/getFaceGroupList?start={start}&length={length}";
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("start", start);
        urlVariables.put("length", length);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class, urlVariables);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 人脸搜索接口
     */
    @Test
    public void searchFace() {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);

        //调用 faceGroupCreate() 接口创建分组ID
        String groupId = "5f9698af6feeeab9f228b8aa";
        Map<String,Object>map = new HashMap();
        map.put("groupId",groupId);
        map.put("imgBase64",encodedText);

        HttpEntity<Object> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/faceSearchV1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void sexyGet() {
        //image_url后为待测试的图片的路径
        String requestUrl = gatewayUrl + "/neuhub/cvImage?image_url=https://smartpart-public.s3.cn-north-1.jdcloud-oss.com/Demo1.jpg";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void localCvImage() {
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/localCvImage";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void food() throws Exception {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);
        // Picture类为自创的实体类
        Picture pic = new Picture(encodedText);
        ObjectMapper objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(pic);
        HttpEntity<String> requestEntity = new HttpEntity<>(value);
        String requestUrl = gatewayUrl + "/neuhub/FoodApi";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void politiciansRecognition() {
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        //以下参数仅为示例值
        String requestUrl = gatewayUrl + "/neuhub/PoliticiansRecognition";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void selfieSegmentation() throws Exception {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);
        // Picture类为自创的实体类
        Picture pic = new Picture(encodedText);
        ObjectMapper objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(pic);
        HttpEntity<String> requestEntity = new HttpEntity<>(value);
        String requestUrl = gatewayUrl + "/neuhub/SelfieSeg";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void snapShop() {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);
        //以下参数仅为示例值
        //todo channel_id 为test 需向管理员申请一个专属的
        String channelId = "test";
        String request = String.format("channel_id=%s&&imgBase64=%s", channelId, encodedText);
        HttpEntity<String> requestEntity = new HttpEntity<>(request);
        String requestUrl = gatewayUrl + "/neuhub/snapshop";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void comment() {
        Map<String, String> map = new HashMap<>();
        map.put("text", comment);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/CommentTag";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void lexer() {
        int type = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("text", comment);
        map.put("type", type);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/lexer";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void sentiment() {
        //type为情感模型的类型
        int type = 1;
        //body请求参数
        Sentiment sentiment = new Sentiment(type, comment);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Sentiment> requestEntity = new HttpEntity<>(sentiment, httpHeaders);
        String requestUrl = gatewayUrl + "/neuhub/sentiment";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void similarity() {
        //body请求参数
        Map<String, Object> postParameters = new HashMap<>();
        postParameters.put("text1", comment);
        postParameters.put("text2", commentCompare);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(postParameters);
        String requestUrl = gatewayUrl + "/neuhub/similarity";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void systax() {
        Map<String, String> map = new HashMap<>();
        map.put("text", comment);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/parser";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void textClassification() {
        Map<String, String> map = new HashMap<>();
        map.put("text", comment);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/textClassification";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void ocrIdCard() {
        //需要传一张真实的身份证的照片
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/ocr_idcard";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void ocrInvoice() {
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/ocr_invoice";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void ocrUniversal() {
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/ocr_universal";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }


    @Test
    public void ocrVehicleRecognition() {
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/ocr_vehicle_recognition";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * domain为语音识别领域，其他参数值请参考文档，applicationId为产品ID，requestId为请求语音串标识码，
     * sequenceId为语音分段传输的分段号，asrProtocol为通信协议版本号，netState为
     * 客户端网络状态，applicator为应用者，AsrEncode构造函数后参数分别为音频声道
     * 数、音频格式和采样率，AsrProperty构造函数后参数为是否开启服务端自动断尾、
     * 语音识别的请求格式AsrEncode、各平台的机型信息和客户端版本号
     */
    @Test
    public void asr() {
        //header示例参数
        String domain = "search";
        String applicationId = "search-app";

        //TODO 没有调通
        //String requestId = "65845428-de85-11e8-9517-040973d59a1e";
        String requestId = UUID.randomUUID().toString();
        int sequenceId = -1;
        int asrProtocol = 1;
        int netState = 2;
        int applicator = 1;
        byte[] data = dataBinary(picture);
        AsrEncode asrEncode = new AsrEncode(1, "wav", 16000);
        AsrProperty property = new AsrProperty(false, asrEncode, "Linux", "0.0.0.1");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Domain", domain);
        httpHeaders.set("Application-Id", applicationId);
        httpHeaders.set("Request-Id", requestId);
        httpHeaders.set("Sequence-Id", Integer.toString(sequenceId));
        httpHeaders.set("Asr-Protocol", Integer.toString(asrProtocol));
        httpHeaders.set("Net-State", Integer.toString(netState));
        httpHeaders.set("Applicator", Integer.toString(applicator));
        httpHeaders.set("property", property.toString());
        HttpEntity<Object> requestEntity = new HttpEntity<>(data, httpHeaders);
        //以下参数仅为示例值
        String requestUrl = gatewayUrl + "/neuhub/asr";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * serviceType为服务类型，requestId为请求语音串标识码，sequenceId为文本分段传输的分段号，
     * protocol为通信协议版本号，netState为客户端网络状态，applicator为应用者，TtsParameters
     * 构造函数后内容依次为文本编码格式 、音频编码格式、音色、音量、语速和采样率，TtsProperty构
     * 造函数后内容依次为各平台的机型信息、客户端版本号和TtsParameters
     */
    @Test
    public void tts() {
        String serviceType = "synthesis";


        //TODO 没有调通 没有返回音频编码
        //String requestId = "65845428-de85-11e8-9517-040973d59a1e";
        String requestId = UUID.randomUUID().toString();
        int sequenceId = 1;
        int protocol = 1;
        int netState = 1;
        int applicator = 1;
        TtsParameters ttsParameters = new TtsParameters("1", "1", "0", "2.0", "1.0", "24000");
        TtsProperty property = new TtsProperty("Linux", "0.0.0.1", ttsParameters);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Service-Type", serviceType);
        httpHeaders.set("Request-Id", requestId);
        httpHeaders.set("Sequence-Id", Integer.toString(sequenceId));
        httpHeaders.set("Protocol", Integer.toString(protocol));
        httpHeaders.set("Net-State", Integer.toString(netState));
        httpHeaders.set("Applicator", Integer.toString(applicator));
        httpHeaders.set("property", property.toString());
        HttpEntity<String> requestEntity = new HttpEntity<>("你好，京东！", httpHeaders);

        String requestUrl = gatewayUrl + "/neuhub/tts";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void ocrBankcard() {
        //需要传一张真实的身份证的照片
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/ocr_bankcard";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void ocrBusiness() {
        //需要传一张真实的身份证的照片
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/ocr_business";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void garbageImageSearch() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        Map<String, Object> map = new HashMap<>();
        map.put("imgBase64", imageBase64);
        map.put("cityId", "310000");
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/garbageImageSearch";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void garbageVoiceSearch() {
        AsrEncode asrEncode = new AsrEncode(1, "amr", 16000, 0);
        AsrProperty property = new AsrProperty(false, asrEncode, "Linux", "0.0.0.1");
        String cityId = "310000";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("property", property.toString());
        httpHeaders.set("cityId", cityId);

        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data, httpHeaders);
        //以下参数仅为示例值
        String requestUrl = gatewayUrl + "/neuhub/garbageVoiceSearch";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void garbageTextSearch() {
        Map<String, Object> map = new HashMap<>();
        map.put("text", comment);
        map.put("cityId", "310000");
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/garbageTextSearch";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void humanParsing() {
        //需要传一张真实的身份证的照片
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/human_parsing";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"items":[{"cid_list":[{"root":"上衣","score":0.97397727},{"root":"服饰内衣","score":0.9999118},{"root":"男装","score":0.96508706},{"root":"卫衣","score":0.98625517}],"rectangle":{"bottom":732.54833984375,"confidence":0.9975353479385376,"left":145.329833984375,"right":643.0061645507812,"top":113.27703857421875}}],"message":"Inference Succeed!","status":"0"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 11:50 上午
     */
    @Test
    public void personreid() {
        //需要传一张真实的身份证的照片
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/personreid";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"items":[{"cid_list":[{"root":"配饰","score":0.9999292},{"root":"医药保健","score":0.9999993},{"root":"护理护具","score":0.99999964},{"root":"口罩","score":0.9999999}],"rectangle":{"bottom":325.3965377807617,"confidence":0.9167974591255188,"left":96.97844505310059,"right":417.9494857788086,"top":37.289772033691406}}],"message":"Inference Succeed!","status":"0"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 10:10 上午
     */
    @Test
    public void image_recognition_product() {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "text/plain");
        String value = String.format("imgBase64=%s", encodedText);
        HttpEntity requestEntity = new HttpEntity<>(value, httpHeaders);


        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("imgBase64", encodedText);

        Map<String, Object> map = new HashMap<>();
        map.put("imgBase64", encodedText);
        // HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map, httpHeaders);
        // String requestUrl = gatewayUrl + "/neuhub/productRecognize";
        String requestUrl = gatewayUrl + "/neuhub/image_recognition_product";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void vehicleDetection() {
        //需要传一张真实的身份证的照片
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/vehicle_detection";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void imageSearchIndex() {

        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/index";
        List<Docs> docsList = new ArrayList<>();
        Docs docs = new Docs();
        docs.setImage_name("Milk_salt_soda_v1");
        docs.setImage_content(imageBase64);
        docs.setInfo("info");
        docsList.add(docs);
        Map<String, Object> map = new HashMap<>();
        map.put("collection_name", comment);
        map.put("docs", docsList);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void imageSearchTask() {
        String task_id = "3548/蔬菜/1576589636.5655253";
        String requestUrl = gatewayUrl + "/neuhub/task?task_id=" + task_id;

        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void imageSearch() {

        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/search";

        Map<String, Object> map = new HashMap<>();
        map.put("collection_name", comment);
        map.put("query_content", imageBase64);
        map.put("top_k", 50);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    public static String encodeImgageToBase64(String url) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        File file = new File(url);
        ByteArrayOutputStream outputStream = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
    }

    /**
     * {"code":"10000","charge":true,"remain":974,"remainTimes":974,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"color_list":[{"hex":"#BAB2AC","percentage":0.3593597412109375,"rgb":"186,178,172"},{"hex":"#D4CABF","percentage":0.2919769287109375,"rgb":"212,202,191"},{"hex":"#A39E9B","percentage":0.2889862060546875,"rgb":"163,158,155"},{"hex":"#787070","percentage":0.041473388671875,"rgb":"120,112,112"},{"hex":"#494342","percentage":0.0182037353515625,"rgb":"73,67,66"}],"status_code":"0","status_message":"success"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 5:47 下午
     */
    @Test
    public void extract_img_colors() {

        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/extract_img_colors";
        int color_count = 5;
        String value = "image=" + imageBase64 + "&color_count=" + color_count;
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity(value);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":995,"remainTimes":995,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"bboxes":[{"Confidence":0.20836183428764343,"bbox":[7,636,76,719],"class_name":"object"},{"Confidence":0.18810199201107025,"bbox":[33,638,96,719],"class_name":"object"},{"Confidence":0.1625920683145523,"bbox":[489,464,638,635],"class_name":"object"},{"Confidence":0.15883946418762207,"bbox":[125,728,179,795],"class_name":"object"},{"Confidence":0.15545311570167542,"bbox":[22,740,94,797],"class_name":"object"},{"Confidence":0.15373295545578003,"bbox":[573,475,648,648],"class_name":"object"},{"Confidence":0.14955675601959229,"bbox":[100,24,343,219],"class_name":"object"},{"Confidence":0.14159874618053436,"bbox":[57,642,118,720],"class_name":"object"},{"Confidence":0.14019979536533356,"bbox":[486,467,581,579],"class_name":"object"},{"Confidence":0.13248951733112335,"bbox":[0,638,41,721],"class_name":"object"},{"Confidence":0.13235361874103546,"bbox":[99,735,157,797],"class_name":"object"},{"Confidence":0.13051649928092957,"bbox":[656,505,708,681],"class_name":"object"},{"Confidence":0.1296822428703308,"bbox":[434,450,625,687],"class_name":"object"},{"Confidence":0.1291021853685379,"bbox":[150,728,203,795],"class_name":"object"},{"Confidence":0.1268327534198761,"bbox":[254,641,289,696],"class_name":"object"},{"Confidence":0.12598690390586853,"bbox":[11,626,121,716],"class_name":"object"},{"Confidence":0.12511569261550903,"bbox":[368,728,413,797],"class_name":"object"},{"Confidence":0.12410488724708557,"bbox":[629,723,684,795],"class_name":"object"},{"Confidence":0.12117989361286163,"bbox":[352,727,399,797],"class_name":"object"},{"Confidence":0.12105372548103333,"bbox":[269,641,305,695],"class_name":"object"},{"Confidence":0.11994653195142746,"bbox":[462,728,515,790],"class_name":"object"},{"Confidence":0.1194823831319809,"bbox":[239,641,272,696],"class_name":"object"},{"Confidence":0.11943613737821579,"bbox":[256,721,305,796],"class_name":"object"},{"Confidence":0.11774037033319473,"bbox":[213,652,251,699],"class_name":"object"},{"Confidence":0.11716287583112717,"bbox":[465,603,513,703],"class_name":"object"},{"Confidence":0.11682609468698502,"bbox":[239,720,288,797],"class_name":"object"},{"Confidence":0.11677271872758865,"bbox":[440,728,491,793],"class_name":"object"},{"Confidence":0.11646470427513123,"bbox":[52,739,121,797],"class_name":"object"},{"Confidence":0.11621517688035965,"bbox":[223,714,272,794],"class_name":"object"},{"Confidence":0.11618538200855255,"bbox":[326,726,378,797],"class_name":"object"},{"Confidence":0.11617251485586166,"bbox":[395,729,440,796],"class_name":"object"},{"Confidence":0.11570960283279419,"bbox":[262,616,296,696],"class_name":"object"},{"Confidence":0.11568797379732132,"bbox":[284,639,321,695],"class_name":"object"},{"Confidence":0.1155451238155365,"bbox":[653,723,708,795],"class_name":"object"},{"Confidence":0.11434587836265564,"bbox":[247,618,280,696],"class_name":"object"},{"Confidence":0.11410341411828995,"bbox":[485,728,540,789],"class_name":"object"},{"Confidence":0.11397726833820343,"bbox":[316,635,354,697],"class_name":"object"},{"Confidence":0.11388843506574631,"bbox":[606,724,661,796],"class_name":"object"},{"Confidence":0.11369414627552032,"bbox":[417,465,578,627],"class_name":"object"},{"Confidence":0.11273546516895294,"bbox":[333,636,371,697],"class_name":"object"},{"Confidence":0.11261255294084549,"bbox":[300,636,338,696],"class_name":"object"},{"Confidence":0.11242323368787766,"bbox":[164,737,218,798],"class_name":"object"},{"Confidence":0.11172764003276825,"bbox":[76,653,134,721],"class_name":"object"},{"Confidence":0.11077481508255005,"bbox":[349,636,387,698],"class_name":"object"},{"Confidence":0.11029742658138275,"bbox":[194,652,239,698],"class_name":"object"},{"Confidence":0.11029518395662308,"bbox":[278,612,312,696],"class_name":"object"},{"Confidence":0.11002067476511002,"bbox":[294,724,346,797],"class_name":"object"},{"Confidence":0.10923691093921661,"bbox":[424,728,470,794],"class_name":"object"},{"Confidence":0.10771875083446503,"bbox":[122,659,181,716],"class_name":"object"},{"Confidence":0.1076522096991539,"bbox":[523,729,584,788],"class_name":"object"},{"Confidence":0.10730298608541489,"bbox":[208,722,256,797],"class_name":"object"},{"Confidence":0.10725757479667664,"bbox":[326,614,362,698],"class_name":"object"},{"Confidence":0.1063307598233223,"bbox":[294,611,329,697],"class_name":"object"},{"Confidence":0.10604537278413773,"bbox":[452,465,489,561],"class_name":"object"},{"Confidence":0.10505066812038422,"bbox":[387,620,429,701],"class_name":"object"},{"Confidence":0.10501842945814133,"bbox":[231,620,264,695],"class_name":"object"},{"Confidence":0.10475017875432968,"bbox":[99,658,157,718],"class_name":"object"},{"Confidence":0.1041857972741127,"bbox":[270,715,322,794],"class_name":"object"},{"Confidence":0.10392242670059204,"bbox":[362,642,405,700],"class_name":"object"},{"Confidence":0.10381366312503815,"bbox":[404,615,446,703],"class_name":"object"},{"Confidence":0.10298680514097214,"bbox":[580,726,637,794],"class_name":"object"},{"Confidence":0.10267627239227295,"bbox":[228,655,266,700],"class_name":"object"},{"Confidence":0.10227160900831223,"bbox":[555,729,614,791],"class_name":"object"},{"Confidence":0.10172883421182632,"bbox":[671,718,721,791],"class_name":"object"},{"Confidence":0.10172398388385773,"bbox":[407,733,455,798],"class_name":"object"},{"Confidence":0.10012178868055344,"bbox":[477,593,531,698],"class_name":"object"}],"message":"Inference Succeed!","status":"0"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 5:51 下午
     */
    @Test
    public void detect_shelf_product() {

        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/detect_shelf_product";
        String value = "imgBase64=" + imageBase64;
        HttpEntity requestEntity = new HttpEntity<>(value);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":997,"remainTimes":997,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"outfits":[{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/133670/21/8178/76082/5f44a251E8ddf72c0/d63ae197eb0b31dc.jpg","item_name":"伊云佳 破洞九分牛仔裤男印花烫钻浅灰色裤子弹力修身小脚休闲青年9分潮裤 883# 27(2尺)"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/126140/16/10811/100037/5f4486c5E43f165ba/df2220f6d30cb0e7.jpg","item_name":"℘十几岁初中生高中生18岁大学生穿的工装裤子男士秋季新款潮牌束脚休闲长裤潮流百搭宽松直筒九分男裤 浅蓝 L"}],"score":0.5115331980963349},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/133670/21/8178/76082/5f44a251E8ddf72c0/d63ae197eb0b31dc.jpg","item_name":"伊云佳 破洞九分牛仔裤男印花烫钻浅灰色裤子弹力修身小脚休闲青年9分潮裤 883# 27(2尺)"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/131961/32/8165/100037/5f449f88Ed8a79eb8/584dd46e5b887e06.jpg","item_name":"2020年夏季休闲长裤男潮牌薄款韩版潮流百搭工装裤直筒宽松九分夏天裤子 6602黑色 L"}],"score":0.5115331980963349},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/133670/21/8178/76082/5f44a251E8ddf72c0/d63ae197eb0b31dc.jpg","item_name":"伊云佳 破洞九分牛仔裤男印花烫钻浅灰色裤子弹力修身小脚休闲青年9分潮裤 883# 27(2尺)"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/128142/8/10855/99671/5f44a1fdEac8d830c/e877098ff6f17178.jpg","item_name":"木子胜春秋款男士休闲裤男宽松直筒九分裤男百搭潮流束脚裤子男 蓝色 S"}],"score":0.5108855821566454},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/133670/21/8178/76082/5f44a251E8ddf72c0/d63ae197eb0b31dc.jpg","item_name":"伊云佳 破洞九分牛仔裤男印花烫钻浅灰色裤子弹力修身小脚休闲青年9分潮裤 883# 27(2尺)"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/126743/11/10731/223936/5f44c0c3E281e44c8/6c2466c3e31678bd.jpg","item_name":"【旅游日常穿的】泰国大象裤男女情侣裤夏季灯笼裤宽松大码显瑜伽裤沙滩海边度假2020 绿色高腰(20年新款) 系带款加大(3尺5腰围内)"}],"score":0.4962124623021198},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/133670/21/8178/76082/5f44a251E8ddf72c0/d63ae197eb0b31dc.jpg","item_name":"伊云佳 破洞九分牛仔裤男印花烫钻浅灰色裤子弹力修身小脚休闲青年9分潮裤 883# 27(2尺)"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/120072/40/10756/63454/5f44809aEa8134c3c/1e5c392eccee720d.jpg","item_name":"花花公子2020春夏季新品松紧裤腰青年韩版收口小脚抽绳长裤潮流时尚百搭运动休闲裤男宽松直筒束脚裤 MF-8818咖啡色 3XL"}],"score":0.4961087151490199},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/125910/33/10655/87615/5f44b5f3Ec30a1e9f/d270f89f5ac36365.jpg","item_name":"℘袋鼠/DaiShu高端轻奢国际大牌潮牌中青年休闲商务男士穿的绅士男装春季复古做旧水洗男士哈伦牛仔裤 兰白 28"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/126140/16/10811/100037/5f4486c5E43f165ba/df2220f6d30cb0e7.jpg","item_name":"℘十几岁初中生高中生18岁大学生穿的工装裤子男士秋季新款潮牌束脚休闲长裤潮流百搭宽松直筒九分男裤 浅蓝 L"}],"score":0.5080620980859872},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/125910/33/10655/87615/5f44b5f3Ec30a1e9f/d270f89f5ac36365.jpg","item_name":"℘袋鼠/DaiShu高端轻奢国际大牌潮牌中青年休闲商务男士穿的绅士男装春季复古做旧水洗男士哈伦牛仔裤 兰白 28"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/131961/32/8165/100037/5f449f88Ed8a79eb8/584dd46e5b887e06.jpg","item_name":"2020年夏季休闲长裤男潮牌薄款韩版潮流百搭工装裤直筒宽松九分夏天裤子 6602黑色 L"}],"score":0.5080620980859872},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/125910/33/10655/87615/5f44b5f3Ec30a1e9f/d270f89f5ac36365.jpg","item_name":"℘袋鼠/DaiShu高端轻奢国际大牌潮牌中青年休闲商务男士穿的绅士男装春季复古做旧水洗男士哈伦牛仔裤 兰白 28"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/128142/8/10855/99671/5f44a1fdEac8d830c/e877098ff6f17178.jpg","item_name":"木子胜春秋款男士休闲裤男宽松直筒九分裤男百搭潮流束脚裤子男 蓝色 S"}],"score":0.5074144821462977},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/125910/33/10655/87615/5f44b5f3Ec30a1e9f/d270f89f5ac36365.jpg","item_name":"℘袋鼠/DaiShu高端轻奢国际大牌潮牌中青年休闲商务男士穿的绅士男装春季复古做旧水洗男士哈伦牛仔裤 兰白 28"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/126743/11/10731/223936/5f44c0c3E281e44c8/6c2466c3e31678bd.jpg","item_name":"【旅游日常穿的】泰国大象裤男女情侣裤夏季灯笼裤宽松大码显瑜伽裤沙滩海边度假2020 绿色高腰(20年新款) 系带款加大(3尺5腰围内)"}],"score":0.49274136229177223},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/125910/33/10655/87615/5f44b5f3Ec30a1e9f/d270f89f5ac36365.jpg","item_name":"℘袋鼠/DaiShu高端轻奢国际大牌潮牌中青年休闲商务男士穿的绅士男装春季复古做旧水洗男士哈伦牛仔裤 兰白 28"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/120072/40/10756/63454/5f44809aEa8134c3c/1e5c392eccee720d.jpg","item_name":"花花公子2020春夏季新品松紧裤腰青年韩版收口小脚抽绳长裤潮流时尚百搭运动休闲裤男宽松直筒束脚裤 MF-8818咖啡色 3XL"}],"score":0.4926376151386723}],"status_code":"0","status_message":"success"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 5:53 下午
     */
    @Test
    public void recommend_outfits() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/recommend_outfits";
        String value = "image=" + imageBase64 + "&outfit_num=" + 10;
        HttpEntity requestEntity = new HttpEntity<>(value);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"failed_list":[],"message":"SUCCESS","successful_list":["204780573"]}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 9:46 上午
     */
    @Test
    public void product_image_create_product() {
        String requestUrl = gatewayUrl + "/neuhub/product_image_create_product";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "ErgouWarehouse");
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("product_id", "204780573");
        map1.put("product_name", "guy's windbreaker");
        map1.put("category", "服装");
        mapList.add(map1);
        paramMap.put("product_list", mapList);
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":498,"remainTimes":498,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"failed_list":[],"message":"SUCCESS","task_id":"3548/ErgouWarehouse/1603763201.548129"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 9:49 上午
     */
    @Test
    public void product_image_add() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/product_image_add";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "ErgouWarehouse");
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("product_id", "204780573");
        map1.put("image_content", imageBase64);
        map1.put("image_id", "chuanda.jpg");
        map1.put("image_info", "这是个卫衣");
        mapList.add(map1);
        paramMap.put("image_list", mapList);
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":496,"remainTimes":496,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"failed_list":[],"message":"SUCCESS","pending_list":[],"succeeded_list":[{"image_id":"chuanda.jpg","product_id":"204780573"}],"task_status":"FINISHED"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 9:52 上午
     */
    @Test
    public void product_image_status() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/product_image_status";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("task_id", "3548/ErgouWarehouse/1603763201.548129");
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":494,"remainTimes":494,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"images":[{"image_id":"chuanda.jpg","image_info":"这是个卫衣","product_id":"204780573","product_name":"guy's windbreaker","similarity":0.9663157821214234}],"message":"SUCCESS","rectangle":[{"bottom":728.2052612304688,"left":203.5562744140625,"right":596.8760375976562,"top":138.70172119140625}]}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 9:58 上午
     */
    @Test
    public void product_image_search() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/product_image_search";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("image_content", imageBase64);
        paramMap.put("collection_name", "ErgouWarehouse");
        paramMap.put("category", "服装");
        paramMap.put("topk", "10");
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":493,"remainTimes":493,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"image_list":[{"image_id":"chuanda.jpg","image_info":"这是个卫衣"}],"message":"SUCCESS"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 9:59 上午
     */
    @Test
    public void product_image_info() {
        String requestUrl = gatewayUrl + "/neuhub/product_image_info";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "ErgouWarehouse");
        paramMap.put("product_id", "204780573");
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":492,"remainTimes":492,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"failed_list":[],"message":"SUCCESS","successful_list":[{"image_id":"chuanda.jpg","product_id":"204780573"}]}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 10:02 上午
     */
    @Test
    public void product_image_del_image() {
        String requestUrl = gatewayUrl + "/neuhub/product_image_del_image";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "ErgouWarehouse");
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("product_id", "204780573");
        map1.put("image_id", "chuanda.jpg");
        mapList.add(map1);
        paramMap.put("image_id_list", mapList);
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":491,"remainTimes":491,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"failed_list":[],"message":"SUCCESS","successful_list":["204780573"]}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 10:04 上午
     */
    @Test
    public void product_image_del_product() {
        String requestUrl = gatewayUrl + "/neuhub/product_image_del_product";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "ErgouWarehouse");
        List<String> ids = new ArrayList<>();
        ids.add("204780573");
        paramMap.put("product_id_list", ids);
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":490,"remainTimes":490,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"info":"[ErgouWarehouse] is deleting.","message":"SUCCESS"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 10:05 上午
     */
    @Test
    public void product_image_del_collection() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/product_image_del_collection";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "ErgouWarehouse");
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"category1":"口罩","category2":"暂无","code":0,"extra_info":[],"is_medical":0,"message":"SUCCESS","spec_no":"暂无"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 2:02 下午
     */
    @Test
    public void medical_material_recognize() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/medical_material_recognize";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("image_content", imageBase64);
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":498,"remainTimes":498,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":1,"message":"ok","result":{"total":11,"list":[{"sku_count":280,"avg_price":4650,"view_count":13350000,"order_rate":0.116,"name":"对开门国产高端冰箱市场","id":0},{"sku_count":310,"avg_price":6189,"view_count":6750000,"order_rate":0.0677,"name":"十字对开门冰箱市场","id":1},{"sku_count":290,"avg_price":4730,"view_count":5990000,"order_rate":0.0604,"name":"多门国产冰箱市场","id":2},{"sku_count":190,"avg_price":2370,"view_count":5980000,"order_rate":0.1452,"name":"三门国产高端冰箱市场","id":3},{"sku_count":270,"avg_price":2900,"view_count":5270000,"order_rate":0.2087,"name":"双门冰箱市场","id":4},{"sku_count":80,"avg_price":2290,"view_count":4140000,"order_rate":0.0713,"name":"对开门国产中低端冰箱市场","id":5},{"sku_count":140,"avg_price":2450,"view_count":3520000,"order_rate":0.1145,"name":"迷你冰箱市场","id":6},{"sku_count":100,"avg_price":1200,"view_count":3520000,"order_rate":0.17,"name":"三门国产中低端冰箱市场","id":7},{"sku_count":140,"avg_price":9680,"view_count":3250000,"order_rate":0.0225,"name":"对开门进口品牌冰箱市场","id":8},{"sku_count":120,"avg_price":14000,"view_count":1250000,"order_rate":0.0053,"name":"多门进口品牌冰箱市场","id":9}],"size":10}}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 2:16 下午
     */
    @Test
    public void marketSegmentationAnalyse() {
        String requestUrl = gatewayUrl + "/neuhub/marketSegmentationAnalyse?page_size=10&page_now=1&category=1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":1,"message":"ok","result":{"total":235,"list":[{"brandname_full":"海尔（Haier）","brand_recognition_score":20.0,"brand_reputation_score":5.0,"brand_marketvalue_score":35.0,"brand_search_score":10.0,"brand_sale_score":25.0,"brand_loyalty_score":21.721543876823016,"brand_compete_score":117.72154387682302,"id":0},{"brandname_full":"美的（Midea）","brand_recognition_score":20.0,"brand_reputation_score":3.858269194668669,"brand_marketvalue_score":20.970974324763585,"brand_search_score":5.922975409687451,"brand_sale_score":16.060292296883617,"brand_loyalty_score":24.076805323027614,"brand_compete_score":91.60584621495991,"id":1},{"brandname_full":"TCL","brand_recognition_score":19.977876106194692,"brand_reputation_score":1.058809419519012,"brand_marketvalue_score":3.8484068559662656,"brand_search_score":1.39995824736877,"brand_sale_score":5.151746569047768,"brand_loyalty_score":2.8665321158908568,"brand_compete_score":34.52854271898263,"id":2},{"brandname_full":"奥克斯（AUX）","brand_recognition_score":19.95575221238938,"brand_reputation_score":0.0,"brand_marketvalue_score":0.9482510403561961,"brand_search_score":1.414496378348495,"brand_sale_score":2.60927544372156,"brand_loyalty_score":3.2580253273838147,"brand_compete_score":28.18580040219945,"id":3},{"brandname_full":"容声（Ronshen）","brand_recognition_score":19.95575221238938,"brand_reputation_score":2.4099451431908725,"brand_marketvalue_score":12.704741041080455,"brand_search_score":3.20343785183327,"brand_sale_score":9.473874006682298,"brand_loyalty_score":8.155583026023487,"brand_compete_score":56.36903040418427,"id":4},{"brandname_full":"创维（Skyworth）","brand_recognition_score":19.911504424778762,"brand_reputation_score":0.9310796153765931,"brand_marketvalue_score":2.3650152683625403,"brand_search_score":0.6387727471684577,"brand_sale_score":3.1320938768617275,"brand_loyalty_score":1.177599781084472,"brand_compete_score":28.343930609828146,"id":5},{"brandname_full":"海信（Hisense）","brand_recognition_score":19.911504424778762,"brand_reputation_score":0.8474490540850593,"brand_marketvalue_score":2.3544616449662543,"brand_search_score":1.068538029978512,"brand_sale_score":2.5333158547360624,"brand_loyalty_score":4.501072480334274,"brand_compete_score":31.377422740854897,"id":6},{"brandname_full":"云米（VIOMI）","brand_recognition_score":19.911504424778762,"brand_reputation_score":0.5095008656112466,"brand_marketvalue_score":2.452144062271606,"brand_search_score":0.8873484098301427,"brand_sale_score":1.611012729208322,"brand_loyalty_score":1.629540473092848,"brand_compete_score":27.114919654855107,"id":7},{"brandname_full":"奥马（Homa）","brand_recognition_score":19.88938053097345,"brand_reputation_score":0.4948219760966147,"brand_marketvalue_score":1.1511293654745172,"brand_search_score":0.402858600657901,"brand_sale_score":1.68845348935936,"brand_loyalty_score":0.8503687162187576,"brand_compete_score":24.573888008109297,"id":8},{"brandname_full":"美菱（MeiLing）","brand_recognition_score":19.88938053097345,"brand_reputation_score":1.7792847756711094,"brand_marketvalue_score":7.780770796902353,"brand_search_score":1.3613560140127403,"brand_sale_score":5.908018570854097,"brand_loyalty_score":3.7011947987721934,"brand_compete_score":40.71814016944962,"id":9}],"size":10}}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 2:22 下午
     */
    @Test
    public void brandCompeteAnalyse() {
        String requestUrl = gatewayUrl + "/neuhub/brandCompeteAnalyse?page_size=10&page_now=1&category=1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":494,"remainTimes":494,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"message":"ok","result":{"after_change_uv_socre":93.46,"before_change_dict":{"90度开门":"不具有","AC+净味":"不具有","DEO净味":"不具有","LTC蓝晶净味":"不具有","PST杀菌":"不具有","TABT除菌":"不具有","一体成型":"具有","一体照明":"不具有","三循环":"不具有","价格":1150,"体积":"196L","假日功能":"不具有","光合保鲜":"不具有","光触媒抗菌":"不具有","全冷气存鲜":"不具有","全开式抽屉":"不具有","全环绕气流":"不具有","净离子杀菌":"不具有","制冰":"不具有","制冷类型":"混冷","动力气流":"不具有","压缩机":"定频","双变频":"不具有","双循环":"具有","发酵功能":"不具有","复古":"不具有","多路送风":"不具有","多重杀菌":"不具有","定制格局":"不具有","宽幅变温":"不具有","宽度":"61-65cm","嵌入式":"不具有","干湿分储":"不具有","底部散热":"不具有","微冻保鲜":"不具有","微晶保鲜":"不具有","急速净味":"不具有","抗菌门封":"不具有","故障智能检测":"不具有","时尚吧台":"不具有","显示器":"具有","智能WI-FI":"不具有","智能保鲜":"不具有","智能变频":"不具有","智能杀菌":"不具有","水分子激活保鲜":"不具有","泡菜发酵":"不具有","涂层":"不具有","涡流动态杀菌":"不具有","深度":"61-65cm","温控方式":"电脑控温","温湿精控":"不具有","独立变温室":"不具有","环保内胆":"不具有","生物保鲜":"不具有","真空仓":"不具有","矢量变频":"不具有","立体蒸发器":"不具有","立体除菌":"不具有","简约风格":"不具有","精细分储":"不具有","纤细边框":"不具有","纤薄设计":"不具有","纳米水离子除菌净味":"不具有","纳米除臭":"不具有","线性变频":"不具有","细胞级养鲜":"不具有","维他养鲜":"不具有","能效等级":"二级","脱氧触媒净味":"不具有","自动低温补偿":"不具有","自动悬停门":"不具有","臻材室":"不具有","蝶门美食窗":"不具有","负氧离子养鲜":"不具有","贴合橱柜":"不具有","速食盘设计":"不具有","金属酒架":"不具有","银离子除菌净味":"不具有","长效净味":"不具有","门中门":"不具有","门的款式":"三门","防串味":"具有","防倾倒设计":"不具有","隐形门把手":"不具有","零度保鲜":"不具有","面板材质":"彩钢","颜色":"金色","食物过期提醒":"不具有","饮水功能":"不具有","高度":"160.1-170cm"},"before_change_uv_socre":87.85,"changed_dict":{"价格":[1150,900],"显示器":["具有","不具有"],"智能杀菌":["不具有","具有"],"温控方式":["电脑控温","机械控温"]}},"used_time":649}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 2:42 下午
     */
    @Test
    public void productUpgrade() {
        String requestUrl = gatewayUrl + "/neuhub/productUpgrade";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        Map<String, Object> map = new HashMap<>();
        map.put("sku", "100003831465");
        String body = JSON.toJSONString(map);
        HttpEntity httpEntity = new HttpEntity(body, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":483,"remainTimes":483,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"message":"ok","result":{"design_dict":{"90度开门":"不具有","AC+净味":"不具有","DEO净味":"不具有","LTC蓝晶净味":"不具有","PST杀菌":"不具有","TABT除菌":"不具有","一体成型":"不具有","一体照明":"不具有","三循环":"不具有","价格":1040,"体积":"175L","假日功能":"具有","光合保鲜":"不具有","光触媒抗菌":"不具有","全冷气存鲜":"不具有","全开式抽屉":"不具有","全环绕气流":"不具有","净离子杀菌":"不具有","制冰":"不具有","制冷类型":"风冷","动力气流":"不具有","压缩机":"定频","双变频":"不具有","双循环":"不具有","发酵功能":"不具有","复古":"不具有","多路送风":"不具有","多重杀菌":"不具有","定制格局":"不具有","宽幅变温":"不具有","宽度":"60cm及以下","嵌入式":"不具有","干湿分储":"不具有","底部散热":"不具有","微冻保鲜":"不具有","微晶保鲜":"不具有","急速净味":"不具有","抗菌门封":"不具有","故障智能检测":"不具有","时尚吧台":"不具有","显示器":"不具有","智能WI-FI":"具有","智能保鲜":"不具有","智能变频":"不具有","智能杀菌":"不具有","水分子激活保鲜":"不具有","泡菜发酵":"不具有","涂层":"不具有","涡流动态杀菌":"不具有","深度":"51-55cm","温控方式":"机械控温","温湿精控":"不具有","独立变温室":"不具有","环保内胆":"具有","生物保鲜":"不具有","真空仓":"不具有","矢量变频":"不具有","立体蒸发器":"不具有","立体除菌":"不具有","简约风格":"具有","精细分储":"不具有","纤细边框":"不具有","纤薄设计":"不具有","纳米水离子除菌净味":"不具有","纳米除臭":"不具有","线性变频":"不具有","细胞级养鲜":"不具有","维他养鲜":"不具有","能效等级":"二级","脱氧触媒净味":"不具有","自动低温补偿":"不具有","自动悬停门":"不具有","臻材室":"不具有","蝶门美食窗":"不具有","负氧离子养鲜":"不具有","贴合橱柜":"不具有","速食盘设计":"不具有","金属酒架":"不具有","银离子除菌净味":"不具有","长效净味":"不具有","门中门":"不具有","门的款式":"双门","防串味":"不具有","防倾倒设计":"不具有","隐形门把手":"不具有","零度保鲜":"不具有","面板材质":"钢化玻璃","颜色":"银色","食物过期提醒":"不具有","饮水功能":"不具有","高度":"140.1-150cm"},"uv_score":92.13},"used_time":594}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 3:11 下午
     */
    @Test
    public void productCustom() {
        String requestUrl = gatewayUrl + "/neuhub/productCustom";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        Map<String, Object> map = new HashMap<>();
        map.put("brand", "康佳");
        map.put("doorType", "双门");
        HttpEntity httpEntity = new HttpEntity(map, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":1000,"message":"success","res":{"analysis_list":[{"appear":41.94054615017829,"basic_fun":58.478470618911615,"brand":33.006242634562966,"chara_fun":30.0,"item_sku_id":1293744,"price":83.41870976674363},{"appear":59.841843453859354,"basic_fun":33.41643482827408,"brand":33.006242634562966,"chara_fun":65.0,"item_sku_id":100004013943,"price":51.994991132020004},{"appear":59.841843453859354,"basic_fun":30.0,"brand":33.006242634562966,"chara_fun":65.0,"item_sku_id":100007119438,"price":30.0},{"appear":62.42162044101015,"basic_fun":99.1359981418841,"brand":100.0,"chara_fun":82.5,"item_sku_id":4717300,"price":66.31667447929976},{"appear":99.99999999999999,"basic_fun":100.0,"brand":30.0,"chara_fun":100.0,"item_sku_id":100002796189,"price":100.0},{"appear":30.0,"basic_fun":58.478470618911615,"brand":33.006242634562966,"chara_fun":30.0,"item_sku_id":2312215,"price":72.69486254950516}],"sku_list":{"input_sku_id":"2312215","sku_compet_list":["1293744","100004013943","100007119438","4717300","100002796189"]}}}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 3:28 下午
     */
    @Test
    public void compete_analysis() {
        String requestUrl = gatewayUrl + "/neuhub/compete_analysis";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(3);
        params.add("sku_id", "2312215");
        HttpEntity httpEntity = new HttpEntity(params, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"text":"How do you like it?","code":"12000"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 3:31 下午
     */
    @Test
    public void nmt() {
        String requestUrl = gatewayUrl + "/neuhub/nmt?type=1&query=你好";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":497,"remainTimes":497,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":"1000","msg":"Request OK!","result":{"score":0.52870667,"words":{"word1":"电脑","word2":"平板"}}}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 3:37 下午
     */
    @Test
    public void similar() {
        String requestUrl = gatewayUrl + "/neuhub/similar";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, String> params = new HashMap<>();
        params.put("word1", "电脑");
        params.put("word2", "平板");
        Object body;
        HttpEntity httpEntity = new HttpEntity(params, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":"1000","msg":"Request OK!","result":{"word":"快递","dimension":300,"vector":[-0.037911,0.167084,0.138183,0.116943,0.069095,0.212419,-0.116308,0.058488,-0.275309,-0.329532,-0.193921,0.142162,-1.230227,-0.293185,0.273049,0.171599,-0.151035,-0.05102,0.018167,0.084426,0.150819,-0.034304,0.319141,-0.291756,0.265164,-0.012452,0.36238,0.529566,0.359141,-0.033559,0.025293,-0.241511,-0.15321,0.291486,-0.228813,0.204874,-0.388871,-1.084903,0.06182,0.004443,-0.218892,-0.379701,-0.006696,0.065591,0.0166,-0.306468,-0.040063,-0.21588,-0.03083,0.355667,-0.120361,0.819872,-0.180082,0.406822,0.07096,-0.029594,0.195672,0.274622,0.239646,0.057883,-0.038688,0.401486,0.963427,-0.571029,-0.120841,0.51343,-0.177278,0.185079,-0.579952,0.099199,-0.220018,0.300598,-0.325692,0.598343,0.553905,-0.214731,0.036638,0.02326,0.9815,-0.353119,-0.439797,-0.129576,0.189542,0.242864,0.001991,-0.66613,-0.168538,-0.597678,-0.112697,0.01833,-0.058606,0.203445,0.239567,0.072739,0.053458,0.228007,0.360398,-0.190158,-0.372746,0.204533,0.477332,-0.442126,-0.089082,0.179749,0.194156,-0.136108,-0.591195,0.205773,0.352406,0.340998,0.77172,0.250099,-0.044077,0.149916,-0.35116,-0.111114,-0.700207,0.976227,0.267899,-0.134144,-0.172125,-0.251711,0.078909,-0.30218,-0.145877,-0.05497,0.602489,0.51856,0.181968,-0.599109,0.094678,-0.186928,0.374335,0.262312,-0.035161,0.110218,-0.125815,-0.663861,-0.180383,-0.002755,0.18194,-0.533794,-0.063088,0.42061,-0.091996,0.218498,-0.029708,0.136975,-0.852604,-0.342475,-0.043089,-0.377562,0.015,-0.330567,-0.137931,-0.04559,-0.050755,-0.081761,0.106078,-0.165312,0.406102,0.199186,0.125872,0.018433,0.020505,0.012398,0.058236,-0.303899,-0.708292,-0.188794,-0.134696,0.746445,0.53082,-0.15328,-0.074665,0.326486,0.961386,0.172559,-0.391814,-0.561391,0.111984,0.531029,-0.272769,-0.297516,-0.040657,0.224285,0.137471,0.189438,-0.110474,0.060339,0.124136,0.674977,-0.246781,-0.479907,-0.280123,-0.027731,0.177524,-0.077416,0.463686,0.127706,0.283156,-0.431895,-0.374185,0.37579,-0.203099,0.309054,-0.722817,-0.86738,-0.521901,0.070132,0.148766,0.345367,-0.111663,0.234061,-0.010744,0.008113,-0.083656,-0.574575,-0.311329,0.587948,0.606915,0.441596,-0.107999,-0.201121,0.486181,-0.579378,0.210087,-0.991549,0.071253,0.653587,0.269964,0.105729,0.056637,-0.234406,0.142797,-0.441458,-0.836428,0.177324,-0.058937,-0.147898,0.641219,0.284513,-0.05259,-0.226084,0.811699,0.08592,0.195313,0.109894,0.072802,-0.067763,-0.035919,0.693025,-0.136339,-0.128742,0.524283,-0.207525,-0.536324,-0.085432,-0.04106,0.015434,-0.227281,-0.473043,-0.249679,0.045114,0.101295,0.199026,-0.071585,-0.401166,0.242846,0.372944,0.404762,0.389531,-0.105451,-0.012071,-0.051103,-0.283482,0.444593,0.270422,0.472096,-0.074616,-0.445827,0.275619,-0.576021,-0.767331,-0.456695,-0.206172,-0.024667,-0.529994,0.24623,-0.165747,1.031492,-0.006329,0.014437,0.079204,-0.349535,-0.065707,0.164571,-0.584908,-0.036687,-0.07459]}}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 3:39 下午
     */
    @Test
    public void word_vector() {
        String requestUrl = gatewayUrl + "/neuhub/word_vector";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, String> params = new HashMap<>();
        params.put("word", "快递");
        Object body;
        HttpEntity httpEntity = new HttpEntity(params, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void phrase_extract() {
        String requestUrl = gatewayUrl + "/neuhub/phrase_extract?document=算法工程师的算法能力特别重要";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":49,"remainTimes":49,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":200,"data":{"title":"展印 天然绿檀木 梳子","content":"天然绿檀木，梳宽齿乳腺。这款梳子的梳齿采用的是优质的原料制作而成，可以很好的按摩头皮，促进头部血液循环，让你的头发更加健康。","contentList":[{"content":"天然绿檀木，梳宽齿乳腺。这款梳子的梳齿采用的是优质的原料制作而成，可以很好的按摩头皮，促进头部血液循环，让你的头发更加健康。","score":1.0},{"content":"梳子采用传统手工打磨制作而成，梳齿间距合理，可以按摩头部穴位，促进头皮血液循环，让头发更加柔顺。天然的绿檀木梳，能更好的呵护你的秀发，同时还能放松身心，舒缓压力。","score":1.0},{"content":"这是一款很好的按摩梳，梳子使用了天然的梳宽齿檀木制作而成，不含有毒有害的物质，可以促进血液循环，让你的头发更加健康。而且它的手柄是乳腺的，很有弹性，也是很适合用来送朋友。","score":1.0}]},"version":"1.0.8.O"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 4:07 下午
     */
    @Test
    public void create_article() {
        String requestUrl = gatewayUrl + "/neuhub/create_article?sku=52119743815&numContent=3";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"best_answer":"又见面啦！"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 4:10 下午
     */
    @Test
    public void chatbot() {
        String requestUrl = gatewayUrl + "/neuhub/chatbot?context=你好";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":999,"remainTimes":999,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":200,"data":[{"ppl":105.49150085449219,"items":[{"word":"这","prob":0.000046},{"word":"是","prob":0.000371},{"word":"一","prob":0.021668},{"word":"款","prob":0.011721},{"word":"无","prob":0.011929},{"word":"袖","prob":0.001146},{"word":"连","prob":0.012524},{"word":"衣","prob":0.191675},{"word":"裙","prob":0.616922},{"word":"，","prob":0.006174},{"word":"蝴","prob":0.0000040},{"word":"蝶","prob":0.984052},{"word":"结","prob":0.210745},{"word":"装","prob":0.016445},{"word":"饰","prob":0.117097},{"word":"甜","prob":0.004208},{"word":"美","prob":0.523187},{"word":"大","prob":0.001476},{"word":"方","prob":0.021803},{"word":"。","prob":0.000563}]}],"msg":""}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 4:12 下午
     */
    @Test
    public void language_smooth() {
        String requestUrl = gatewayUrl + "/neuhub/language_smooth?sentence=这是一款无袖连衣裙，蝴蝶结装饰甜美大方。";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":999,"remainTimes":999,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":200,"data":{"synonym":["智慧果","平安果","超凡子","平波","滔婆","天然子","苹果公司","小米","黑莓","苹果电脑","iPad","新产品","产品","洋葱","草莓","苹果新","苹果iphone","苹果的iphone","iphone产品"],"inputText":"苹果"}}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 4:14 下午
     */
    @Test
    public void corpus_synonyms() {
        String requestUrl = gatewayUrl + "/neuhub/corpus_synonyms?word=苹果";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":996,"remainTimes":996,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":200,"data":{"ss":0.041114978227711665,"results":{"0":["算法对噪音和异常点比较的敏感","算法的可解释度比较强","对于不是凸的数据集比较难收敛","调参相对于传统聚类算法稍复杂"],"1":["现代足球运动起源于英国","美国职业篮球联赛有大约30个球队","休斯顿火箭队总经理发表涉华不正当言论","足球和篮球是世界上参与人数最多的两项运动"]}}}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 4:23 下午
     */
    @Test
    public void corpus_aggregation() {
        String requestUrl = gatewayUrl + "/neuhub/corpus_aggregation";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        Map<String, Object> params = new HashMap<>();
        params.put("checkAlgo", "kmeans");
        params.put("inputText", Arrays.asList("算法对噪音和异常点比较的敏感", "算法的可解释度比较强", "对于不是凸的数据集比较难收敛", "调参相对于传统聚类算法稍复杂", "现代足球运动起源于英国", "美国职业篮球联赛有大约30个球队", "休斯顿火箭队总经理发表涉华不正当言论", "足球和篮球是世界上参与人数最多的两项运动").toArray());
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":999,"remainTimes":999,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":200,"data":[["幂","颖","洋"]],"msg":""}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 4:25 下午
     */
    @Test
    public void language_cloze() {
        String requestUrl = gatewayUrl + "/neuhub/language_cloze?sentence=这是杨*同款包包，精选优质皮料制作";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":999,"remainTimes":999,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":200,"data":["解放军","移交"]}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 4:27 下午
     */
    @Test
    public void hotwords() {
        String requestUrl = gatewayUrl + "/neuhub/hotwords";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        Map<String, Object> params = new HashMap<>();
        params.put("input_text", "据港媒报道，中区军用码头今早时起正式移交驻港解放军，划为军事禁区。驻军进一步采纳特区政府建议，除面向维港的一面外，军用码头其余三面采用活动栏栅，可以打开供市民通行");
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void universalForPesticide() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String value = String.format("imageBase64Str=%s", imageBase64);
        HttpEntity<String> requestEntity = new HttpEntity<>(value);
        String requestUrl = gatewayUrl + "/neuhub/universalForPesticide";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    private String imageBase64(byte[] data) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);
    }

    private byte[] dataBinary(String addr) {
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(addr));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    private void result(ResponseEntity<String> responseEntity) {
        String responseBody = responseEntity.getBody();
        logger.info("调用结果: {}", responseBody);
    }
}


