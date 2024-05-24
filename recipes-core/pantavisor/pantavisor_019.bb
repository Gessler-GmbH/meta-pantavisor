#
# This file was derived from the 'Hello World!' example recipe in the
# Yocto Project Development Manual.
#

DESCRIPTION = "Pantavisor Next Gen System Runtime"
SECTION = "base"
DEPENDS = "cmake libthttp picohttpparser lxc-pv mbedtls zlib pkgconfig-native"
RDEPENDS:${PN} += "lxc-pv e2fsprogs-resize2fs e2fsprogs-e2fsck e2fsprogs-mke2fs cryptsetup libthttp-certs gptfdisk "
RDEPENDS:${PN}:qemumips += "lxc-pv libthttp-certs "
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}_${PV}:"

S = "${WORKDIR}/git"

SRC_URI = "git://github.com/pantavisor/pantavisor.git;protocol=https;branch=master"
SRC_URI += " file://pantavisor-run"
SRC_URI += " file://pantavisor.config"
SRC_URI += " file://pantavisor-embedded.config"
SRC_URI += " file://policies/"
SRC_URI += " file://ssh/"
SRC_URI += " file://rev0json"

SRCREV = "905f44b9a03f4424f7fc575f8b4795bd62b505a1"

FILES:${PN} += " /usr/bin/pantavisor-run"
FILES:${PN} += " /usr/lib"
FILES:${PN} += " /var/pantavisor/storage/trails/0/.pvr/json"
FILES:${PN} += " /usr/share/pantavisor/skel/etc/pantavisor/defaults/groups.json"
FILES:${PN} += " /storage /writable /volumes /exports /pv /etc/pantavisor /lib/ "
FILES:${PN} += " /certs"
FILES:${PN} += " /init"

inherit cmake

OECMAKE_C_FLAGS += "-Wno-unused-result -ldl"

CMAKE_BINARY_DIR = "${S}"

do_install() {
	cmake_do_install
	install -d ${D}/etc
	install -d ${D}/etc/pantavisor
	install -d ${D}/etc/pantavisor/policies
	install -d ${D}/etc/pantavisor/ssh
	install -d ${D}/var/pantavisor/structure
	install -d ${D}/usr/share/pantavisor/skel/etc/pantavisor/defaults
	install -d ${D}/usr/share/pantavisor/skel/writable
	install -d ${D}/usr/share/pantavisor/skel/storage
	install -d ${D}/usr/share/pantavisor/skel/exports
	install -d ${D}/usr/share/pantavisor/skel/configs
	install -d ${D}/usr/share/pantavisor/skel/etc/dropbear
	install -d ${D}/usr/share/pantavisor/skel/volumes
	install -d ${D}/usr/share/pantavisor/skel/pv
	install -d ${D}/usr/share/pantavisor/skel/old
	install -d ${D}/storage
	install -d ${D}/volumes
	install -d ${D}/exports
	install -d ${D}/writable
	install -d ${D}/pv
	install -d ${D}/var/pantavisor/storage/trails/0/.pvr
	install -d ${D}/var/pantavisor/storage/config
	install -d ${D}/var/pantavisor/storage/boot
	install -d ${D}/var/pantavisor/storage/disks
	install -d ${D}/var/pantavisor/root
	install -d ${D}/var/pantavisor/tmpfs
	install -d ${D}/var/pantavisor/ovl/work
	install -d ${D}/var/pantavisor/ovl/upper
	install -d ${D}/lib/pv
	install -d ${D}/usr/lib
	install -m 0644 ${WORKDIR}/policies/* ${D}/etc/pantavisor/policies/
	install -m 0644 ${WORKDIR}/ssh/* ${D}/etc/pantavisor/ssh/
	install -m 0644 ${S}/defaults/groups.json ${D}/usr/share/pantavisor/skel/etc/pantavisor/defaults/groups.json
	install -m 0644 ${WORKDIR}/rev0json ${D}/var/pantavisor/storage/trails/0/.pvr/json
	install -m 0755 ${WORKDIR}/pantavisor-run ${D}/usr/bin/pantavisor-run
	install -m 0755 ${WORKDIR}/pantavisor-run ${D}/usr/bin/pantavisor-run
	if [ -f ${WORKDIR}/pantavisor-installer ]; then
		install -m 0755 ${WORKDIR}/pantavisor-installer ${D}/lib/pv/pantavisor-installer
	fi
	ln -sf ../../lib/pv ${D}/usr/lib/pv
	echo "Yes"
}

