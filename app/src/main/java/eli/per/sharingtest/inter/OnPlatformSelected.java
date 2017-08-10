package eli.per.sharingtest.inter;

/**
 * 平台选择接口
 */
public interface OnPlatformSelected {
    /**
     * 平台选择后的方法
     * @param platformID 对应平台的ID
     */
    void getSelectedPlatform(int platformID);
}
