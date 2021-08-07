const blogs = {
    init: function () {
        const _this = this;

        let btnSubscriptions = document.getElementsByName('btn-subscription')
        let blogIds = document.getElementsByName('blogId')
        let chkMailReceiptStatus = document.getElementById('chk-mail-receipt-status')

        if (btnSubscriptions != null) {
            let index = 0

            // 구독 이벤트
            for (const btnSubscription of btnSubscriptions) {
                if (btnSubscription.value == null || btnSubscription.value === '') {
                    let blogId = blogIds[index].dataset.id

                    btnSubscription.addEventListener('click', function () {
                        _this.saveSubscription(blogId)
                    });
                } else {
                    let subscriptionId = btnSubscription.value

                    btnSubscription.addEventListener('click', function () {
                        _this.deleteSubscription(subscriptionId)
                    });
                }

                index++
            }
        }

        // 메일 알림 여부
        if (chkMailReceiptStatus != null) {
            chkMailReceiptStatus.addEventListener('click', function () {
                let checked = chkMailReceiptStatus.getAttribute('checked')
                if (checked === 'checked') {
                    chkMailReceiptStatus.setAttribute('checked', 'unchecked')
                } else {
                    chkMailReceiptStatus.setAttribute('checked', 'checked')
                }

                _this.updateMailReceiptStatus(checked);
            });
        }

        // 블로그 활성 이벤트
        let btnUsageStatuses = document.getElementsByName('btn-usageStatus');

        let index = 0
        for (const btnUsageStatus of btnUsageStatuses) {
            let blogId = blogIds[index].dataset.id
            let usageStatus = btnUsageStatus.value
            let msg = '';

            if (usageStatus === 'USE') {
                msg = '비활성'
            } else {
                msg = '활성'
            }

            btnUsageStatus.addEventListener('click', function () {
                _this.updateUsageStatus(blogId, usageStatus, msg)
            });

            index++
        }
    },
    saveSubscription: function (pBlogId) {
        if (confirm('구독 하시겠습니까?')) {
            $.ajax({
                type: 'POST',
                url: '/api/blogs/subscription/' + pBlogId,
            }).done(function () {
                alert('구독이 완료 되었습니다.')
                window.location.href = '/blogs'
            }).fail(function (error) {
                alert(JSON.stringify(error))
            })
        }
    },
    deleteSubscription: function (pSubscriptionId) {
        if (confirm('구독 취소 하시겠습니까?')) {
            $.ajax({
                type: 'DELETE',
                url: 'api/blogs/subscription/' + pSubscriptionId,
            }).done(function () {
                alert('구독 취소가 되었습니다.')
                window.location.href = '/blogs'
            }).fail(function (error) {
                alert(JSON.stringify(error))
            })
        }
    },
    updateUsageStatus: function (pBlogId, pUsageStatus, pMsg) {
        let lastUrl = ''

        if (pUsageStatus === 'USE') {
            lastUrl = '/unusedStatus'
        } else {
            lastUrl = '/useStatus'
        }
        if (confirm(pMsg + ' 하시겠습니까?')) {
            $.ajax({
                type: 'PUT',
                url: 'api/admin/blogs/' + pBlogId + lastUrl,
            }).done(function () {
                alert(pMsg + ' 되었습니다.')
                window.location.href = '/blogs'
            }).fail(function (error) {
                alert(JSON.stringify(error))
            })
        }
    },
    updateMailReceiptStatus: function (checked) {
        let param = ''

        if (checked === 'checked') {
            param = 'N'
        } else {
            param = 'Y'
        }
        $.ajax({
            type: 'PUT',
            url: 'api/blog/mailReceiptStatus/' + param,
        }).done(function () {

        }).fail(function (error) {
            alert(JSON.stringify(error))
        })
    }
};

blogs.init()